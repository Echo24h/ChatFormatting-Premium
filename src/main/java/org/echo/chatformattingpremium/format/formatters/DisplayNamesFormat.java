package org.echo.chatformattingpremium.format.formatters;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.echo.chatformattingpremium.ChatFormatting;

public class DisplayNamesFormat {

    public static BaseComponent[] getPlayerChatDisplayName(ChatFormatting plugin, Player player) {
        return plugin.getDisplayNames().getDisplayNameChat(player);
    }

    public static void setPlayerDisplayName(ChatFormatting plugin, Player player) {

        if (!plugin.getDisplayNames().isDisplayInTabList() && !plugin.getDisplayNames().isAbovePlayersHeads())
            return ;

        BaseComponent[] displayNameComponents = plugin.getDisplayNames().getDisplayName(player);
        String displayName = BaseComponent.toLegacyText(displayNameComponents);

        if (plugin.getDisplayNames().isDisplayInTabList())
            player.setPlayerListName(" " + displayName + " "); // Set prefix in the tablist
        if (plugin.getDisplayNames().isAbovePlayersHeads()) {
            player.setCustomNameVisible(true); // Afficher le custom nametag
            player.setCustomName(displayName); // Définir le custom nametag
        }

        player.setDisplayName(displayName); // Set prefix above the player's
    }

    public static void resetAllDisplayNames(ChatFormatting plugin) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setPlayerListName(player.getName());
            player.setDisplayName(player.getName());
        }
    }
}

