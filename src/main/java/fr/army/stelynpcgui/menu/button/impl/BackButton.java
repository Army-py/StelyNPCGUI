package fr.army.stelynpcgui.menu.button.impl;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.army.stelynpcgui.menu.NPCMenu;
import fr.army.stelynpcgui.menu.button.ButtonType;
import fr.army.stelynpcgui.menu.button.NPCButton;

public class BackButton extends NPCButton {

    private NPCMenu previousMenu = null;

    public BackButton(String character, int slot, Material material, String name, int amount, ButtonType buttonType,
            List<String> lore) {
        super(character, slot, material, name, amount, buttonType, lore);
    }

    @Override
    public void onClick(InventoryClickEvent clickEvent) {
        Player player = (Player) clickEvent.getWhoClicked();
        if (previousMenu == null) {
            player.closeInventory();
        } else {
            player.openInventory(previousMenu.createInventory());
        }
    }

    public NPCMenu getPreviousMenu() {
        return previousMenu;
    }

    public void setPreviousMenu(NPCMenu previousMenu) {
        this.previousMenu = previousMenu;
    }

    public static BackButton mapButton(NPCButton button) {
        return new BackButton(button.getCharacter(), button.getSlot(), button.getMaterial(), button.getName(),
                button.getAmount(),
                button.getButtonType(), button.getLore());
    }
}
