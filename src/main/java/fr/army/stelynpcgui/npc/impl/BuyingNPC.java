package fr.army.stelynpcgui.npc.impl;

import org.bukkit.entity.Player;

import fr.army.stelynpcgui.menu.impl.BuyingMenu;
import fr.army.stelynpcgui.npc.NPC;
import net.citizensnpcs.api.event.NPCClickEvent;

public class BuyingNPC extends NPC {

    private final BuyingMenu menu;

    public BuyingNPC(String name, BuyingMenu menu) {
        super(name);
        this.menu = menu;
    }

    @Override
    public void onClick(NPCClickEvent clickEvent) {
        final Player player = clickEvent.getClicker();
        final String npcName = clickEvent.getNPC().getName();

        if (this.getName().equals(npcName)){
            player.openInventory(menu.createInventory());
        }
    }
    
}
