package fr.army.stelynpcgui.menu.button.impl;

import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.army.stelynpcgui.StelyNPCGUIPlugin;
import fr.army.stelynpcgui.menu.button.ButtonType;
import fr.army.stelynpcgui.menu.button.NPCButton;
import fr.army.stelynpcgui.util.builder.ItemBuilder;
import fr.army.stelynpcgui.util.manager.EconomyManager;
import fr.army.stelynpcgui.util.manager.MessageManager;

public class BuyingButton extends NPCButton {

    private final StelyNPCGUIPlugin plugin = StelyNPCGUIPlugin.getInstance();
    private final YamlConfiguration itemsTranslation = plugin.getItemsTranslation();
    private final EconomyManager economyManager = plugin.getEconomyManager();

    private Material buyingMaterial = null;
    private String buyingItemName = null;
    private String buyingSkullTexture = null;
    private double buyingPrice = 0.0;
    private int buyingQuantity = 0;

    private String npcName = null;

    public BuyingButton(String character, int slot, Material material, String name, int amount, ButtonType buttonType,
            List<String> lore) {
        super(character, slot, material, name, amount, buttonType, lore);
    }


    @Override
    public void onClick(InventoryClickEvent clickEvent) {
        final Player player = (Player) clickEvent.getWhoClicked();            
        final String itemMaterial = buyingMaterial.toString().toLowerCase();
        
        String itemName = buyingItemName == null
                ? itemMaterial.substring(0, 1).toUpperCase() + itemMaterial.substring(1)
                : buyingItemName;
        if (itemsTranslation.isString(buyingMaterial.toString())){
            itemName = itemsTranslation.getString(buyingMaterial.toString());
        }
        
        final MessageManager messageManager = MessageManager.getInstance()
                    .setPlayerName(player.getName())
                    .setNPCName(npcName)
                    .setItemName(itemName)
                    .setItemAmount(buyingQuantity)
                    .setItemPrice(buyingPrice);


        if (economyManager.checkMoneyPlayer(player, buyingPrice)) {
            // player.getInventory().addItem(new ItemStack(buyingMaterial, buyingQuantity));
            player.getInventory().addItem(
                    ItemBuilder.getItem(buyingMaterial, buyingQuantity, buyingItemName, Collections.emptyList(), buyingSkullTexture, isGlow()));

            economyManager.removeMoneyPlayer(player, buyingPrice);
            player.sendMessage(messageManager.getMessage("buy"));
        } else {
            player.sendMessage(messageManager.getMessage("not-enough-money"));
        }
    }


    public Material getBuyingMaterial() {
        return buyingMaterial;
    }

    public String getBuyingItemName() {
        return buyingItemName;
    }

    public String getBuyingSkullTexture() {
        return buyingSkullTexture;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public int getBuyingQuantity() {
        return buyingQuantity;
    }

    public String getNPCName() {
        return npcName;
    }

    public BuyingButton setBuyingMaterial(Material sellingMaterial) {
        this.buyingMaterial = sellingMaterial;
        return this;
    }

    public BuyingButton setBuyingItemName(String sellingItemName) {
        this.buyingItemName = sellingItemName;
        return this;
    }

    public BuyingButton setBuyingSkullTexture(String sellingSkullTexture) {
        this.buyingSkullTexture = sellingSkullTexture;
        return this;
    }

    public BuyingButton setBuyingPrice(int sellingPrice) {
        this.buyingPrice = sellingPrice;
        return this;
    }

    public BuyingButton setBuyingQuantity(int sellingQuantity) {
        this.buyingQuantity = sellingQuantity;
        return this;
    }

    public BuyingButton setNPCName(String npcName) {
        this.npcName = npcName;
        return this;
    }

    public static BuyingButton mapButton(NPCButton button) {
        BuyingButton b = new BuyingButton(button.getCharacter(), button.getSlot(), button.getMaterial(), button.getName(),
                button.getAmount(),
                button.getButtonType(), button.getLore());
        b.setSkullTexture(button.getSkullTexture());
        b.setGlow(button.isGlow());
        return b;
    }
}
