package org.echo.chatformattingpremium.listener;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.echo.chatformattingpremium.ChatFormatting;
import org.echo.chatformattingpremium.Utils;

public class UtilsListener {

    public static boolean sendBroadcastMessage(ChatFormatting plugin, BaseComponent[] formattedMessage) {

        if (formattedMessage == null)
            return false;
        // Broadcast the formatted message
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.spigot().sendMessage(formattedMessage);
            if (plugin.getMyConfig().isChatNotificationSound())
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK,0.1f, 10f);
        }
        plugin.getServer().getConsoleSender().sendMessage(Utils.getFormatedMessage(TextComponent.toPlainText(formattedMessage)));
        return true;
    }
}
