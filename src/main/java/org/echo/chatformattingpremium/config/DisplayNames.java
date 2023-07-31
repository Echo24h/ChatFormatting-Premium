package org.echo.chatformattingpremium.config;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.echo.chatformattingpremium.ChatFormatting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayNames {

    public class PrefixGroup {
        private String permission;
        private int priority;
        private String prefix;
        private String suffix;

        public PrefixGroup(String permission, int priority, String prefix, String suffix) {
            this.permission = permission;
            this.priority = priority;
            this.prefix = prefix;
            this.suffix = suffix;
        }

        public String getPermission() {
            return permission;
        }

        public int getPriority() {
            return priority;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }
    }

    private ChatFormatting plugin;
    private File file;
    private YamlConfiguration yaml;
    private List<PrefixGroup> prefixGroups;
    private PrefixGroup basicDisplayName;
    private boolean isAbovePlayersHeads;
    private boolean isDisplayInTabList;
    private int tickUpdate;

    public DisplayNames(ChatFormatting plugin, String filename) {
        this.plugin = plugin;
        this.yaml = loadConfig(filename);
        this.prefixGroups = new ArrayList<>();
        loadDisplayNames();
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

    private void loadDisplayNames() {

        this.basicDisplayName = new PrefixGroup("", 0, yaml.getString("basic-display-name.prefix", ""), yaml.getString("basic-display-name.suffix", ":&f "));

        this.isAbovePlayersHeads = yaml.getBoolean("display-above-players-heads", false);
        this.isDisplayInTabList = yaml.getBoolean("display-in-tab-list", false);

        this.tickUpdate = yaml.getInt("tick-update-period", 100);
        if (this.tickUpdate < 20)
            tickUpdate = 20;

        ConfigurationSection prefixGroupSection = yaml.getConfigurationSection("display-name");
        if (prefixGroupSection != null) {
            for (String key : prefixGroupSection.getKeys(false)) {
                ConfigurationSection groupSection = prefixGroupSection.getConfigurationSection(key);
                if (groupSection != null) {
                    String permission = groupSection.getString("permission");
                    int priority = groupSection.getInt("priority");
                    String prefix = groupSection.getString("prefix");
                    String suffix = groupSection.getString("suffix");
                    PrefixGroup prefixGroup = new PrefixGroup(permission, priority, prefix, suffix);
                    this.prefixGroups.add(prefixGroup);
                }
            }
        }
    }

    public PrefixGroup getBasicDisplayName() {
        return basicDisplayName;
    }

    public List<PrefixGroup> getPrefixGroups() {
        return prefixGroups;
    }

    public BaseComponent[] getDisplayNameChat(Player player) {
        PrefixGroup highestPriorityGroup = null;

        for (PrefixGroup group : this.prefixGroups) {
            if (player.hasPermission(group.getPermission())) {
                if (highestPriorityGroup == null || group.getPriority() > highestPriorityGroup.getPriority()) {
                    highestPriorityGroup = group;
                }
            }
        }


        String display;
        if (highestPriorityGroup != null) {
            display = highestPriorityGroup.getPrefix() + player.getName() + highestPriorityGroup.getSuffix();
        }
        else {
            display = getBasicDisplayName().getPrefix() + player.getName() + getBasicDisplayName().getSuffix();
        }
        return plugin.getFormatManager().formatText(display);
    }

    public BaseComponent[] getDisplayName(Player player) {
        PrefixGroup highestPriorityGroup = null;

        for (PrefixGroup group : this.prefixGroups) {
            if (player.hasPermission(group.getPermission())) {
                if (highestPriorityGroup == null || group.getPriority() > highestPriorityGroup.getPriority()) {
                    highestPriorityGroup = group;
                }
            }
        }

        String display;
        if (highestPriorityGroup != null) {
            display = highestPriorityGroup.getPrefix() + player.getName();
        }
        else {
            display = getBasicDisplayName().getPrefix() + player.getName();
        }
        return plugin.getFormatManager().formatText(display);
    }

    public boolean isAbovePlayersHeads() {
        return isAbovePlayersHeads;
    }

    public boolean isDisplayInTabList() {
        return isDisplayInTabList;
    }

    public int getTickUpdate() { return tickUpdate; }
}
