package org.echo.chatformattingpremium.config;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.echo.chatformattingpremium.ChatFormatting;

import java.io.File;

public class Messages {

    private ChatFormatting plugin;

    private YamlConfiguration yaml;
    private File file;

    // Constructeur de la classe
    public Messages(ChatFormatting plugin, String fileName) {
        this.plugin = plugin;
        this.yaml = loadConfig(fileName);
    }

    // Méthode pour charger les messages depuis le fichier de configuration
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

    // Méthode pour récupérer un message à partir de sa clé
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

    public String getMentionInfo(String senderName) {
        BaseComponent[] baseComponents = getFormattedMessage("mention-info", "{name}", senderName);;
        return new TextComponent(baseComponents).toLegacyText();
    }
    public BaseComponent[] getLinksInfo(String url) {

        BaseComponent[] baseComponents = getMessage("links-info");
        ComponentBuilder componentBuilder = new ComponentBuilder("");
        componentBuilder.append(baseComponents);
        componentBuilder.append(new TextComponent("\n" + ChatColor.GRAY + url));
        return componentBuilder.create();
    }
    public BaseComponent[] getLinksPermission() {
        return getMessage("links-permission");
    }
    public BaseComponent[] getItemPermission() {
        return getMessage("item-permission");
    }
    public BaseComponent[] getMentionPermission() {
        return getMessage("mention-permission");
    }
    public BaseComponent[] getCommandsPermission() {
        return getMessage("commands-permission");
    }

    public BaseComponent[] getServerPlayerJoin(String playerDisplayName) {
        return getFormattedMessage("server-player-join", "{name}", playerDisplayName);
    }

    public BaseComponent[] getServerPlayerWelcome(String playerDisplayName) {
        return getFormattedMessage("server-player-welcome", "{name}", playerDisplayName);
    }

    public BaseComponent[] getServerPlayerQuit(String playerDisplayName) {
        return getFormattedMessage("server-player-quit", "{name}", playerDisplayName);
    }

}
