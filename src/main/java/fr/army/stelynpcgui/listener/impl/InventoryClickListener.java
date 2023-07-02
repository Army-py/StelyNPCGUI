package fr.army.stelynpcgui.listener.impl;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.army.stelynpcgui.StelyNPCGUIPlugin;
import fr.army.stelynpcgui.menu.NPCMenu;

public class InventoryClickListener implements Listener {

    private final StelyNPCGUIPlugin plugin;
    
    public InventoryClickListener(StelyNPCGUIPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler()
    public void onInventoryClick(InventoryClickEvent event){
        if (!(event.getView().getTopInventory().getHolder() instanceof NPCMenu)) {
            return;
        }

        if (event.getClickedInventory() == null || !event.getClickedInventory().equals(event.getView().getTopInventory())){
            return;
        }

        event.setCancelled(true);

        if (event.getCurrentItem() == null){
            return;
        }

        ((NPCMenu) event.getView().getTopInventory().getHolder()).onClick(event);
        return;
    }
}
