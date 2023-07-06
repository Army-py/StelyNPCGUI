package fr.army.stelynpcgui.menu.impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.army.stelynpcgui.menu.NPCMenu;
import fr.army.stelynpcgui.menu.button.ButtonType;
import fr.army.stelynpcgui.menu.button.NPCButton;
import fr.army.stelynpcgui.menu.button.impl.BackButton;
import fr.army.stelynpcgui.menu.button.impl.BuyingButton;
import fr.army.stelynpcgui.util.builder.menu.MenuBuilder;
import fr.army.stelynpcgui.util.builder.menu.MenuBuilderResult;

public class BuyingMenu extends NPCMenu {

    public BuyingMenu(String title, int size) {
        super(title, size);
    }

    public BuyingMenu(String title, int size, NPCButton[] buttons) {
        super(title, size, buttons);
    }

    public static BuyingMenu createInstance(String npcName, String configName) {
        MenuBuilderResult builderResult = MenuBuilder.getInstance().loadMenu(configName + ".yml");
        NPCMenu menu = builderResult.getMenu();
        YamlConfiguration config = builderResult.getConfig();

        for (String section : config.getConfigurationSection("items").getKeys(false)) {
            ConfigurationSection itemSection = config.getConfigurationSection("items." + section);

            if (itemSection.getString("button-type").equals(ButtonType.BUYING.toString())) {
                ConfigurationSection buyingSection = itemSection.getConfigurationSection("buy");

                final Material material = Material.getMaterial(buyingSection.getString("material"));
                final String name = buyingSection.getString("name");
                final String skullTexture = buyingSection.getString("skull-texture") == null
                        || buyingSection.getString("skull-texture").isEmpty() ? null
                                : buyingSection.getString("skull-texture");
                final int price = buyingSection.getInt("price");
                final int quantity = buyingSection.getInt("quantity");

                for (int slot : menu.getSlots(section)) {
                    final BuyingButton button = BuyingButton.mapButton(menu.getButton(slot));
                    button.setBuyingMaterial(material);
                    button.setBuyingItemName(name);
                    button.setBuyingSkullTexture(skullTexture);
                    button.setBuyingPrice(price);
                    button.setBuyingQuantity(quantity);
                    button.setNPCName(npcName);
                    menu.setButton(slot, button);
                }
            } else if (itemSection.getString("button-type").equals(ButtonType.BACK.toString())) {
                for (int slot : menu.getSlots(section)) {
                    final BackButton button = BackButton.mapButton(menu.getButton(slot));
                    menu.setButton(slot, button);
                }
            }
        }

        return new BuyingMenu(menu.getTitle(), menu.getSize(), menu.getButtons());
    }

    @Override
    public Inventory createInventory() {
        final Inventory inventory = Bukkit.createInventory(this, size, title);

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            NPCButton button = getButton(slot);
            ItemStack itemStack = button.getItemStack();
            inventory.setItem(slot, itemStack);
        }

        return inventory;
    }

    @Override
    public void onClick(InventoryClickEvent clickEvent) {
        final int slot = clickEvent.getRawSlot();

        if (getButton(slot) instanceof BuyingButton) {
            final BuyingButton button = (BuyingButton) getButton(slot);
            button.onClick(clickEvent);
        }else if (getButton(slot) instanceof BackButton) {
            final BackButton button = (BackButton) getButton(slot);
            button.setPreviousMenu(previousMenu);
            button.onClick(clickEvent);
        }
    }

}
