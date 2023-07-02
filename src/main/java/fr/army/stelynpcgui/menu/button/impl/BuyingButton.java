package fr.army.stelynpcgui.menu.button.impl;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.army.stelynpcgui.StelyNPCGUIPlugin;
import fr.army.stelynpcgui.menu.button.ButtonType;
import fr.army.stelynpcgui.menu.button.NPCButton;
import fr.army.stelynpcgui.util.manager.EconomyManager;
import fr.army.stelynpcgui.util.manager.MessageManager;

public class BuyingButton extends NPCButton {

    private final StelyNPCGUIPlugin plugin = StelyNPCGUIPlugin.getInstance();
    private final YamlConfiguration itemsTranslation = plugin.getItemsTranslation();
    private final EconomyManager economyManager = plugin.getEconomyManager();

    private Material buyingMaterial = null;
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
        
        String itemName = itemMaterial.substring(0, 1).toUpperCase() + itemMaterial.substring(1);
        if (itemsTranslation.isString(buyingMaterial.toString())){
            itemName = itemsTranslation.getString(buyingMaterial.toString());
        }
        
        final MessageManager messageManager = MessageManager.getInstance()
                    .setPlayerName(player.getName())
                    .setNPCName(npcName)
                    .setItemName(itemName)
                    .setItemAmount(buyingQuantity)
                    .setItemPrice(buyingPrice);


        if (player.getInventory().contains(buyingMaterial, buyingQuantity)) {
            player.getInventory().removeItem(new ItemStack(buyingMaterial, buyingQuantity));

            economyManager.addMoneyPlayer(player, buyingPrice);
            player.sendMessage(messageManager.getMessage("sell"));
        } else {
            // player.sendMessage("§cVous n'avez pas assez de " + getName() + ".");
            player.sendMessage(messageManager.getMessage("not-enough-item"));
        }
    }


    public Material getSellingMaterial() {
        return buyingMaterial;
    }

    public double getSellingPrice() {
        return buyingPrice;
    }

    public int getSellingQuantity() {
        return buyingQuantity;
    }

    public String getNPCName() {
        return npcName;
    }

    public BuyingButton setSellingMaterial(Material sellingMaterial) {
        this.buyingMaterial = sellingMaterial;
        return this;
    }

    public BuyingButton setSellingPrice(int sellingPrice) {
        this.buyingPrice = sellingPrice;
        return this;
    }

    public BuyingButton setSellingQuantity(int sellingQuantity) {
        this.buyingQuantity = sellingQuantity;
        return this;
    }

    public BuyingButton setNPCName(String npcName) {
        this.npcName = npcName;
        return this;
    }

    public static BuyingButton mapButton(NPCButton button) {
        return new BuyingButton(button.getCharacter(), button.getSlot(), button.getMaterial(), button.getName(),
                button.getAmount(),
                button.getButtonType(), button.getLore());
    }
}
