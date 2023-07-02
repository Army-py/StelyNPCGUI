package fr.army.stelynpcgui.menu.impl;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.army.stelynpcgui.menu.NPCMenu;

public class EmptyMenu extends NPCMenu {

    public EmptyMenu(String title, int size) {
        super(title, size);
    }

    @Override
    public void onClick(InventoryClickEvent clickEvent) {
    }

    @Override
    public Inventory createInventory() {
        Inventory inventory = Bukkit.createInventory(this, size, title);
        return inventory;
    }
    
}
