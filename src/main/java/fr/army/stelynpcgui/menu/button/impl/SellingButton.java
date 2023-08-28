package fr.army.stelynpcgui.menu.button.impl;

import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.army.stelynpcgui.StelyNPCGUIPlugin;
import fr.army.stelynpcgui.menu.button.ButtonType;
import fr.army.stelynpcgui.menu.button.NPCButton;
import fr.army.stelynpcgui.util.builder.ItemBuilder;
import fr.army.stelynpcgui.util.manager.EconomyManager;
import fr.army.stelynpcgui.util.manager.MessageManager;

public class SellingButton extends NPCButton {

    private final StelyNPCGUIPlugin plugin = StelyNPCGUIPlugin.getInstance();
    private final YamlConfiguration itemsTranslation = plugin.getItemsTranslation();
    private final EconomyManager economyManager = plugin.getEconomyManager();

    private Material sellingMaterial = null;
    private String sellingItemName = null;
    private String sellingSkullTexture = null;
    private double sellingPrice = 0.0;
    private int sellingQuantity = 0;

    private String npcName = null;

    public SellingButton(String character, int slot, Material material, String name, int amount, ButtonType buttonType,
            List<String> lore) {
        super(character, slot, material, name, amount, buttonType, lore);
    }


    @Override
    public void onClick(InventoryClickEvent clickEvent) {
        final Player player = (Player) clickEvent.getWhoClicked();            
        final String itemMaterial = sellingMaterial.toString().toLowerCase();
        
        String itemName = sellingItemName == null
                ? itemMaterial.substring(0, 1).toUpperCase() + itemMaterial.substring(1)
                : sellingItemName;
        if (itemsTranslation.isString(sellingMaterial.toString())){
            itemName = itemsTranslation.getString(sellingMaterial.toString());
        }
        
        final MessageManager messageManager = MessageManager.getInstance()
                    .setPlayerName(player.getName())
                    .setNPCName(npcName)
                    .setItemName(itemName)
                    .setItemAmount(sellingQuantity)
                    .setItemPrice(sellingPrice);

        ItemStack item = ItemBuilder.getItem(sellingMaterial, sellingQuantity, sellingItemName, Collections.emptyList(),
                sellingSkullTexture, isGlow());
        
        System.out.println("material: " + sellingMaterial.toString());
        System.out.println("quantity: " + sellingQuantity);
        System.out.println("name: " + sellingItemName);
        System.out.println("skull: " + sellingSkullTexture);
        if (player.getInventory().contains(item)) {
            player.getInventory().removeItem(item);
            // player.getInventory().removeItem(item);

            economyManager.addMoneyPlayer(player, sellingPrice);
            player.sendMessage(messageManager.getMessage("sell"));
        } else {
            // player.sendMessage("§cVous n'avez pas assez de " + getName() + ".");
            player.sendMessage(messageManager.getMessage("not-enough-item"));
        }
    }


    public Material getSellingMaterial() {
        return sellingMaterial;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getSellingQuantity() {
        return sellingQuantity;
    }

    public String getNPCName() {
        return npcName;
    }

    public SellingButton setSellingMaterial(Material sellingMaterial) {
        this.sellingMaterial = sellingMaterial;
        return this;
    }

    public SellingButton setSellingItemName(String sellingItemName) {
        this.sellingItemName = sellingItemName;
        return this;
    }

    public SellingButton setSellingSkullTexture(String sellingSkullTexture) {
        this.sellingSkullTexture = sellingSkullTexture;
        return this;
    }

    public SellingButton setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public SellingButton setSellingQuantity(int sellingQuantity) {
        this.sellingQuantity = sellingQuantity;
        return this;
    }

    public SellingButton setNPCName(String npcName) {
        this.npcName = npcName;
        return this;
    }

    public static SellingButton mapButton(NPCButton button) {
        SellingButton b = new SellingButton(button.getCharacter(), button.getSlot(), button.getMaterial(), button.getName(),
                button.getAmount(),
                button.getButtonType(), button.getLore());
        b.setSkullTexture(button.getSkullTexture());
        b.setGlow(button.isGlow());
        return b;
    }
}
