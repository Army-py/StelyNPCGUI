package fr.army.stelynpcgui.menu.button.impl;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.army.stelynpcgui.menu.button.ButtonType;
import fr.army.stelynpcgui.menu.button.NPCButton;

public class EmptyButton extends NPCButton {

    public EmptyButton(String character, int slot, Material material, String name, int amount, ButtonType buttonType,
            List<String> lore) {
        super(character, slot, material, name, amount, buttonType, lore);
    }

    @Override
    public void onClick(InventoryClickEvent clickEvent) {
    }
    
}
