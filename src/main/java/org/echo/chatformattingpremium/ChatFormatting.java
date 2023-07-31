package org.echo.chatformattingpremium;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.echo.chatformattingpremium.commands.Commands;
import org.echo.chatformattingpremium.config.*;
import org.echo.chatformattingpremium.format.FormatManager;
import org.echo.chatformattingpremium.format.formatters.DisplayNamesFormat;
import org.echo.chatformattingpremium.listener.ChatListener;
import org.echo.chatformattingpremium.listener.PlayerListener;
import org.echo.chatformattingpremium.task.DisplayNameUpdater;

import java.io.File;

public final class ChatFormatting extends JavaPlugin {

    private static ChatFormatting instance;
    private Config config;
    private BadWords badWords;
    private Colors colors;
    private DisplayNames displayNames;
    private Messages messages;

    private FormatManager formatManager;
    private DisplayNameUpdater displayNameUpdater;

    private String minecraftVersion = "";


    @Override
    public void onEnable() {

        setMinecraftVersion();
        reloadPlugin();
        // Register the command and event listener
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getCommand("chat").setExecutor(new Commands(this));
        getCommand("cf").setExecutor(new Commands(this));
        getCommand("chatformatting").setExecutor(new Commands(this));
    }

    public void reloadPlugin() {
        this.instance = this;
        DisplayNamesFormat.resetAllDisplayNames(this);
        this.config = new Config(this, "config.yml");
        this.displayNames = new DisplayNames(this, "displaynames.yml");
        this.colors = new Colors(this, "colors.yml");
        this.badWords = new BadWords(this, "badwords.yml");
        this.messages = new Messages(this, "messages.yml");
        this.formatManager = new FormatManager();
        displayNameUpdater = new DisplayNameUpdater();
        displayNameUpdater.startUpdating();
    }

    @Override
    public void onDisable() {
        displayNameUpdater.cancel();
    }

    public static ChatFormatting getInstance() {
        return instance;
    }

    public FormatManager getFormatManager() { return formatManager; }

    public File getDirectory() {
        return getDataFolder();
    }

    // Constructor and other methods...

    public BadWords getBadWords() {
        return badWords;
    }

    public Colors getColors() {
        return colors;
    }

    public Config getMyConfig() {
        return config;
    }

    public DisplayNames getDisplayNames() {
        return displayNames;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMinecraftVersion() {
        String versionString = Bukkit.getBukkitVersion();
        int firstSeparatorIndex = versionString.indexOf("-");
        this.minecraftVersion = versionString.substring(0, firstSeparatorIndex);
    }

    // Version example : "1.13.2"
    public String getMinecraftVersion() {
        return this.minecraftVersion;
    }

    // Version example : "1.13.2"
    public boolean isMinecraftHigherVersion(String targetVersion) {
        String[] currentVersionParts = this.minecraftVersion.split("\\.");
        String[] targetVersionParts = targetVersion.split("\\.");

        for (int i = 0; i < Math.min(currentVersionParts.length, targetVersionParts.length); i++) {

            int currentVersionPart = Integer.parseInt(currentVersionParts[i]);
            int targetVersionPart = Integer.parseInt(targetVersionParts[i]);

            if (currentVersionPart > targetVersionPart) {
                return true;
            } else if (currentVersionPart < targetVersionPart) {
                return false;
            }
        }

        // If we reach here, the versions have the same parts up to the minimum length
        // If target version has more parts (e.g., 1.13.2 compared to 1.13), then target version is higher
        return currentVersionParts.length <= targetVersionParts.length;
    }
}
