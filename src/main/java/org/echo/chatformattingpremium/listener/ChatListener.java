package org.echo.chatformattingpremium.listener;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.echo.chatformattingpremium.ChatFormatting;

public class ChatListener implements Listener {

    ChatFormatting plugin;

    public ChatListener(ChatFormatting plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        event.setCancelled(true);

        BaseComponent[] message = plugin.getFormatManager().formatPlayerChatMessage(event.getPlayer(), event.getMessage());

        if (message != null)
            UtilsListener.sendBroadcastMessage(plugin, message);
    }
}
