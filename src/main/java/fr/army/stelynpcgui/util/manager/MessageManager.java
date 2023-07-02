package fr.army.stelynpcgui.util.manager;

import org.bukkit.configuration.file.YamlConfiguration;

import fr.army.stelynpcgui.StelyNPCGUIPlugin;


public class MessageManager {

    private static final MessageManager INSTANCE = new MessageManager(StelyNPCGUIPlugin.getInstance());

    private final StelyNPCGUIPlugin plugin;
    private final YamlConfiguration messages;

    private String playerName = null;
    private String NPCName = null;
    private String itemName = null;
    private int itemAmount = 0;
    private double itemPrice = 0.0;

    public MessageManager(StelyNPCGUIPlugin plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }


    public static MessageManager getInstance() {
        return INSTANCE;
    }


    public String getMessageWithoutPrefix(String key) {
        return messages.getString(key);
    }

    public String getMessage(String path) {
        return getPluginPrefix() + replacer(messages.getString(path));
    }

    public MessageManager setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public MessageManager setNPCName(String NPCName) {
        this.NPCName = NPCName;
        return this;
    }

    public MessageManager setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public MessageManager setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
        return this;
    }

    public MessageManager setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }


    private String getPluginPrefix(){
        return replacer(messages.getString("prefix"));
    }


    private String replacer(String message){
        if (playerName != null) message = message.replace("%PLAYER%", playerName);
        if (NPCName != null) message = message.replace("%NPC%", NPCName);
        if (itemName != null) message = message.replace("%ITEM%", itemName);
        if (itemAmount != 0) message = message.replace("%AMOUNT%", String.valueOf(itemAmount));
        if (itemPrice != 0.0) message = message.replace("%PRICE%", String.valueOf(itemPrice));
        message = message.replace("%PLUGIN%", plugin.getName());

        return message;
    }
}
