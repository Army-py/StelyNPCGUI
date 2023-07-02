package fr.army.stelynpcgui.menu.button;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import fr.army.stelynpcgui.util.builder.ItemBuilder;

public abstract class NPCButton {

    private final String character;
    private final int slot;
    private final Material material;
    private final String name;
    private final int amount;
    private final ButtonType buttonType;
    private final List<String> lore;

    private boolean glow;
    private String skullTexture;

    public NPCButton(String character, int slot, Material material, String name, int amount, ButtonType buttonType,
            List<String> lore) {
        this.character = character;
        this.slot = slot;
        this.material = material;
        this.name = name;
        this.amount = amount;
        this.buttonType = buttonType;
        this.lore = lore;
    }

    public abstract void onClick(InventoryClickEvent clickEvent);

    public String getCharacter() {
        return character;
    }

    public int getSlot() {
        return slot;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    @Nullable
    public List<String> getLore() {
        return lore;
    }

    public ItemStack getItemStack(){
        return ItemBuilder.getItem(material, character, name, lore, skullTexture, glow);
    }

    public boolean isGlow() {
        return glow;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }

    public String getSkullTexture() {
        return skullTexture;
    }

    public void setSkullTexture(String skullTexture) {
        this.skullTexture = skullTexture;
    }

}
