package fr.army.stelynpcgui.util.builder.menu;

import org.bukkit.configuration.file.YamlConfiguration;

import fr.army.stelynpcgui.menu.NPCMenu;

public class MenuBuilderResult {
    
    private final NPCMenu menu;
    private final YamlConfiguration config;

    public MenuBuilderResult(NPCMenu menu, YamlConfiguration config) {
        this.menu = menu;
        this.config = config;
    }

    public NPCMenu getMenu() {
        return menu;
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
