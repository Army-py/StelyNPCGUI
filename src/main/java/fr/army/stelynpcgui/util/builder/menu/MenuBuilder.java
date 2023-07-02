package fr.army.stelynpcgui.util.builder.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.army.stelynpcgui.StelyNPCGUIPlugin;
import fr.army.stelynpcgui.menu.NPCMenu;
import fr.army.stelynpcgui.menu.button.ButtonType;
import fr.army.stelynpcgui.menu.button.NPCButton;
import fr.army.stelynpcgui.menu.button.impl.EmptyButton;
import fr.army.stelynpcgui.menu.impl.EmptyMenu;

public class MenuBuilder {
    
    private static final MenuBuilder INSTANCE = new MenuBuilder();

    private final StelyNPCGUIPlugin plugin = StelyNPCGUIPlugin.getInstance();

    public static MenuBuilder getInstance() {
        return INSTANCE;
    }


    public MenuBuilderResult loadMenu(String configName){
        return buildMenu(plugin.getConfigLoader().initFile("menus/" + configName));
    }


    private MenuBuilderResult buildMenu(YamlConfiguration config) {
        final String title = config.getString("title");
        final String[] pattern = config.getStringList("pattern").toArray(String[]::new);
        final List<NPCButton> buttons = new ArrayList<>();

        int size = 0;
        for (int row = 0; row < pattern.length && row < 6; row++) {
            final String line = pattern[row].replace(" ", "");

            for (int column = 0; column < line.length() && column < 9; column++) {
                final char character = line.charAt(column);

                final String path = "items." + character + ".";

                final int slot = row * 9 + column;
                final String material = config.getString(path + "material");
                final String name = config.getString(path + "name");
                final int amount = config.getInt(path + "amount");
                final List<String> lore = config.getStringList(path + "lore");
                final String buttonType = config.getString(path + "button-type");

                final NPCButton button = new EmptyButton(String.valueOf(character), slot, Material.valueOf(material),
                        name, amount,
                        ButtonType.valueOf(buttonType), lore);
                button.setGlow(config.getBoolean(path + "glow"));
                button.setSkullTexture(config.getString(path + "skull-texture"));

                buttons.add(button);
                size++;
            }
        }

        NPCMenu menu = new EmptyMenu(title, size);
        menu.addButtons(buttons.toArray(NPCButton[]::new));

        return new MenuBuilderResult(menu, config);
    }
}
