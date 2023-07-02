package fr.army.stelynpcgui.listener.impl;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.army.stelynpcgui.StelyNPCGUIPlugin;
import fr.army.stelynpcgui.menu.impl.SellingMenu;
import fr.army.stelynpcgui.npc.NPC;
import fr.army.stelynpcgui.npc.NPCType;
import fr.army.stelynpcgui.npc.impl.SellingNPC;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class NPCInteractListener implements Listener {
    
    private final StelyNPCGUIPlugin plugin;
    private final YamlConfiguration config;
    private final Set<NPC> npcs;

    public NPCInteractListener(StelyNPCGUIPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.npcs = new HashSet<>();

        initNPCs();
    }


    @EventHandler
    public void onNpcClick(NPCRightClickEvent event){
        final String npcName = event.getNPC().getName();

        for (NPC npc : npcs) {
            if (npc.getName().equals(npcName)){
                npc.onClick(event);
            }
        }
    }


    private void initNPCs(){
        for (String section : config.getConfigurationSection("npcs").getKeys(false)) {
            ConfigurationSection npcSection = config.getConfigurationSection("npcs." + section);
            final String npcName = npcSection.getString("name");
            final String fileName = npcSection.getString("main-menu");
            final String npcType = npcSection.getString("type");

            NPC npc = null;
            if (npcType.equals(NPCType.SELLING.toString())){
                npc = new SellingNPC(npcName, SellingMenu.createInstance(npcName, fileName));
            }

            if (npc != null){
                npcs.add(npc);
            }
        }
    }
}
