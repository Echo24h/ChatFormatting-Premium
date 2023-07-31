package org.echo.chatformattingpremium.config;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.echo.chatformattingpremium.ChatFormatting;
import org.echo.chatformattingpremium.utils.ColorUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Colors {

    public class GradiantColor {
        private String color1;
        private String color2;

        public GradiantColor(String color1, String color2) {
            this.color1 = color1.substring(1);
            this.color2 = color2.substring(1);
        }
        public String getColor1() {
            return color1;
        }
        public String getColor2() { return color2; }
    }

    private ChatFormatting plugin;
    private File file;
    private YamlConfiguration yaml;
    private static Map<String, GradiantColor> gradiantColors;
    private static Map<String, net.md_5.bungee.api.ChatColor> customColors;

    private boolean isDisableColorVersionAlert;
    public Colors(ChatFormatting plugin, String filename) {
        this.plugin = plugin;
        this.yaml = loadConfig(filename);
        this.gradiantColors = new HashMap<>();
        this.customColors = new HashMap<>();
        loadColors();
    }

    private YamlConfiguration loadConfig(String filename) {
        if (!plugin.getDirectory().exists()) {
            plugin.getDirectory().mkdir();
        }

        this.file = new File(plugin.getDataFolder(), filename);

        if (!file.exists()) {
            plugin.saveResource(filename, false);
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    private void saveConfig() {
        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadColors() {

        ConfigurationSection colorsSection;

        isDisableColorVersionAlert = yaml.getBoolean("disable-color-version-alert", false);
        // CUSTOM
        if (!ChatFormatting.getInstance().isMinecraftHigherVersion("1.16")) {
            if (!isDisableColorVersionAlert)
                ChatFormatting.getInstance().getServer().getConsoleSender().sendMessage(org.bukkit.ChatColor.YELLOW + "[ChatFormatting-Premium] Minecraft allows custom color from 1.16, your version is lower. You can disable this message in color.yml");
        }
        else {
            colorsSection = yaml.getConfigurationSection("custom");
            if (colorsSection != null) {
                for (String key : colorsSection.getKeys(false)) {
                    ConfigurationSection colorSection = colorsSection.getConfigurationSection(key);
                    if (colorSection != null) {
                        ChatColor color = ColorUtils.of(colorSection.getString("color"));
                        this.customColors.put(key, color);
                    }
                }
            }
        }
        // GRADIANT
        colorsSection = yaml.getConfigurationSection("gradiant");
        if (colorsSection != null) {
            for (String key : colorsSection.getKeys(false)) {
                ConfigurationSection colorSection = colorsSection.getConfigurationSection(key);
                if (colorSection != null) {
                    String color1 = colorSection.getString("color1");
                    String color2 = colorSection.getString("color2");
                    GradiantColor color = new GradiantColor(color1, color2);
                    this.gradiantColors.put(key, color);
                }
            }
        }
    }

    public static GradiantColor getGradiantColor(String key) {
        return gradiantColors.getOrDefault(key, null);
    }

    public static ChatColor getColor(String key) {

        ChatColor color = customColors.getOrDefault(key, null);

        if (color != null)
            return color;

        color = ChatColor.getByChar(key.charAt(0));
        return color;
    }

    public static String isGradiantColor(String colorMessage) {

        for (int i = 0; i < colorMessage.length(); i++) {
            String substring = colorMessage.substring(0, i + 1);
            GradiantColor colors = gradiantColors.get(substring);
            if (colors != null) {
                return substring;
            }
        }
        return null;
    }

    public static String isColor(String colorMessage) {

        if (colorMessage.length() < 1)
            return null;

        if (colorMessage.charAt(0) == 'r')
            return null;
        if (colorMessage.charAt(0) == 'l')
            return null;
        if (colorMessage.charAt(0) == 'o')
            return null;
        if (colorMessage.charAt(0) == 'n')
            return null;
        if (colorMessage.charAt(0) == 'm')
            return null;
        if (colorMessage.charAt(0) == 'k')
            return null;

        for (int i = 0; i < colorMessage.length() && i < 10; i++) {
            String substring = colorMessage.substring(0, i + 1);

            ChatColor color = customColors.get(substring);

            if (color != null) {
                return substring;
            }
        }

        if (org.bukkit.ChatColor.getByChar(colorMessage.charAt(0)) != null) {
            return String.valueOf(colorMessage.charAt(0));
        }

        return null;
    }

}
