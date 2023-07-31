package org.echo.chatformattingpremium.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.echo.chatformattingpremium.ChatFormatting;
import org.echo.chatformattingpremium.format.formatters.DisplayNamesFormat;

public class PlayerListener implements Listener {

    ChatFormatting plugin;
    public PlayerListener(ChatFormatting plugin) { this.plugin = plugin; }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String displayName = player.getName();

        DisplayNamesFormat.setPlayerDisplayName(plugin, player);

        event.setJoinMessage(null);
        if (!player.hasPlayedBefore()) {
            UtilsListener.sendBroadcastMessage(plugin, plugin.getMessages().getServerPlayerWelcome(displayName));
        }
        else {
            UtilsListener.sendBroadcastMessage(plugin, plugin.getMessages().getServerPlayerJoin(displayName));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getName();
        event.setQuitMessage(null);
        UtilsListener.sendBroadcastMessage(plugin, plugin.getMessages().getServerPlayerQuit(displayName));
    }
}





