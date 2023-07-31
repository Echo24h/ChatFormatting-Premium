package org.echo.chatformattingpremium.config;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.configuration.file.YamlConfiguration;
import org.echo.chatformattingpremium.ChatFormatting;
import org.echo.chatformattingpremium.Utils;

import java.io.File;
import java.io.IOException;

public class Config {
    private ChatFormatting plugin;
    private File file;
    private YamlConfiguration yaml;
    private String replacementOfLink;
    private String replacementOfItem;
    private String replacementOfMention;
    private boolean isChatNotificationSound;
    private boolean isMentionNotificationSound;

    public Config(ChatFormatting plugin, String file_name) {
        this.plugin = plugin;
        this.yaml = loadConfig(file_name);
        readConfig();
    }

    private YamlConfiguration loadConfig(String file_name) {

        if(!this.plugin.getDirectory().exists()) {
            this.plugin.getDirectory().mkdir();
        }

        this.file = new File(this.plugin.getDataFolder(), file_name);

        if (!this.file.exists()) {
            this.plugin.saveResource(file_name, false);
        }

        return YamlConfiguration.loadConfiguration(this.file);
    }

    private void readConfig() {
        // Read the values from the configuration file
        this.replacementOfLink = yaml.getString("replacement-of-link", "&l&n&o&bLINK");
        this.isChatNotificationSound = yaml.getBoolean("chat-notification-sound", true);
        this.isMentionNotificationSound = yaml.getBoolean("mention-notification-sound", true);
    }

    private void saveConfig() {
        try {
            this.yaml.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BaseComponent[] getMessage(String key) {
        String message = yaml.getString(key);
        if (message != null) {
            return plugin.getFormatManager().formatText(message);
        }
        return new ComponentBuilder("").create();
    }

    // Méthode pour récupérer un message avec des remplacements
    private BaseComponent[] getFormattedMessage(String key, String... replacements) {

        String message = yaml.getString(key);
        if (message == null)
            message = "";

        for (int i = 0; i < replacements.length; i += 2) {
            String placeholder = replacements[i];
            String replacement = replacements[i + 1];
            message = message.replace(placeholder, replacement);
        }
        return plugin.getFormatManager().formatText(message);
    }

    // Getters
    public String getReplacementOfLink() {
        return replacementOfLink;
    }
    public BaseComponent[] getReplacementOfItem(String name) {
        return getFormattedMessage("replacement-of-item", "{name}", name);
    }
    public BaseComponent[] getReplacementOfMention(String name) {
        return getFormattedMessage("replacement-of-mention", "{name}", name);
    }
    public boolean isChatNotificationSound() {
        return isChatNotificationSound;
    }

    public boolean isMentionNotificationSound() {
        return isMentionNotificationSound;
    }
}
