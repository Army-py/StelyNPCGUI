package fr.army.stelynpcgui.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import fr.army.stelynpcgui.menu.button.NPCButton;

public abstract class NPCMenu implements INPCMenu {

    protected final String title;
    protected final int size;
    protected final NPCButton[] buttons;

    protected NPCMenu previousMenu = null;

    protected InventoryHolder holder;

    public NPCMenu(String title, int size) {
        this.title = title;
        this.size = size;
        this.buttons = new NPCButton[size];
    }

    public NPCMenu(String title, int size, NPCButton[] buttons) {
        this.title = title;
        this.size = size;
        this.buttons = buttons;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public NPCButton[] getButtons() {
        return buttons;
    }

    public NPCButton getButton(int slot) {
        return buttons[slot];
    }

    public int[] getSlots(String itemSection) {
        List<Integer> slots = new ArrayList<>();

        for (NPCButton button : buttons) {
            if (button.getCharacter().equals(itemSection))
                slots.add(button.getSlot());
        }

        return slots.stream().mapToInt(i -> i).toArray();
    }

    public NPCMenu getPreviousMenu() {
        return previousMenu;
    }

    public void addButton(NPCButton button) {
        this.buttons[button.getSlot()] = button;
    }

    public void addButtons(NPCButton... buttons){
        for(NPCButton button : buttons){
            this.addButton(button);
        }
    }

    public void setButton(int slot, NPCButton button) {
        this.buttons[slot] = button;
    }

    public void setPreviousMenu(NPCMenu previousMenu) {
        this.previousMenu = previousMenu;
    }


    public abstract Inventory createInventory();

    public abstract void onClick(InventoryClickEvent clickEvent);


    @Override
    public Inventory getInventory() {
        return this.holder.getInventory();
    }
}
