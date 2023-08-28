package fr.army.stelynpcgui.util.builder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;


public class ItemBuilder {
    public static ItemStack getItem(Material material, int amount, String displayName, List<String> lore,
            String headTexture, boolean isGlow) {
        if (material.equals(Material.PLAYER_HEAD) && (headTexture != null && !headTexture.isBlank()))
            return buildCustomHead(headTexture, displayName, lore, null);
    
        return buildItem(material, amount, displayName, lore, isGlow);
    }

    public static ItemStack getHiddenPropertiesItem(Material material, int amount, String displayName, List<String> lore,
            String headTexture, boolean isGlow) {
    
        ItemStack item = getItem(material, amount, displayName, lore, headTexture, isGlow);

        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack buildItem(Material material, int amount, String displayName, List<String> lore, boolean isGlow) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        if (!lore.isEmpty()) {
            List<String> loreList = (List<String>) lore;
            meta.setLore(loreList);
        }

        if (isGlow) {
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        // NamespacedKey key = new NamespacedKey(StelyNPCGUIPlugin.getInstance(),
        // "buttonName");
        // meta.getPersistentDataContainer().set(key, PersistentDataType.STRING,
        // buttonName);

        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getPlayerHead(OfflinePlayer player, String name, List<String> lore) {
        return buildCustomHead(null, name, lore, player);
    }

    private static ItemStack buildCustomHead(String texture, String name, List<String> lore,
            OfflinePlayer player) {

        GameProfile profile = new GameProfile(toUUID(texture), (String) null);
        profile.getProperties().put("textures", new Property("textures", texture));
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = item.getItemMeta();

        // System.out.println("texture: " + texture);
        try {
            Field mtd = meta.getClass().getDeclaredField("profile");
            mtd.setAccessible(true);
            mtd.set(meta, profile);
        } catch (IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
        }

        if (!lore.isEmpty()) {
            List<String> loreList = (List<String>) lore;
            meta.setLore(loreList);
        }

        // if (player != null) {
        // NamespacedKey key = new NamespacedKey(StelyNPCGUIPlugin.getInstance(),
        // "playerName");
        // skullMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING,
        // player.getName());
        // }

        // if (buttonName != null) {
        // NamespacedKey key = new NamespacedKey(StelyNPCGUIPlugin.getInstance(),
        // "buttonName");
        // skullMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING,
        // buttonName);
        // }

        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }


    private static UUID toUUID(String input) {
        long val = input.hashCode();
        return new UUID(val, val);
    }
}
