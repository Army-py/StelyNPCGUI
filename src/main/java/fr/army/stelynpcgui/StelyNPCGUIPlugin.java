package fr.army.stelynpcgui;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.army.stelynpcgui.listener.ListenerLoader;
import fr.army.stelynpcgui.util.loader.ConfigLoader;
import fr.army.stelynpcgui.util.manager.EconomyManager;

public class StelyNPCGUIPlugin extends JavaPlugin {
    
    private static StelyNPCGUIPlugin instance;
    private ConfigLoader configLoader;
    private YamlConfiguration config;
    private YamlConfiguration messages;
    private YamlConfiguration itemsTranslation;
    private EconomyManager economyManager;

    @Override
    public void onEnable() {
        instance = this;
        
        this.configLoader = new ConfigLoader(this);

        this.config = this.configLoader.initFile("config.yml");
        this.messages = this.configLoader.initFile("messages.yml");
        this.itemsTranslation = this.configLoader.initFile("items.yml");
        
        this.economyManager = new EconomyManager(this);

        final ListenerLoader listenerLoader = new ListenerLoader();
        listenerLoader.registerListeners(this);

        getLogger().info("StelyNPCGUI ON");
    }


    @Override
    public void onDisable() {
        getLogger().info("StelyNPCGUI OFF");
    }


    public static StelyNPCGUIPlugin getInstance() {
        return instance;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public YamlConfiguration getMessages() {
        return messages;
    }

    public YamlConfiguration getItemsTranslation() {
        return itemsTranslation;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }
}
