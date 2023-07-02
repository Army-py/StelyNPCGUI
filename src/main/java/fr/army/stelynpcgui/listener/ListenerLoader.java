package fr.army.stelynpcgui.listener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.army.stelynpcgui.StelyNPCGUIPlugin;
import fr.army.stelynpcgui.listener.impl.InventoryClickListener;
import fr.army.stelynpcgui.listener.impl.NPCInteractListener;

public class ListenerLoader {

    public void registerListeners(StelyNPCGUIPlugin plugin) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(plugin), plugin);
        pluginManager.registerEvents(new NPCInteractListener(plugin), plugin);
    }

}
