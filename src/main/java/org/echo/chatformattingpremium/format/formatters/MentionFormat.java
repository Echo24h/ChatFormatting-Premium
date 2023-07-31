package org.echo.chatformattingpremium.format.formatters;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.echo.chatformattingpremium.ChatFormatting;

public class MentionFormat {

    private static ChatFormatting plugin = ChatFormatting.getInstance();

    public static BaseComponent[] formatMention(Player sender, String mention) {

        ComponentBuilder componentBuilder = new ComponentBuilder("");

        if (mention.length() > 0) {
            String target = mention.substring(1);
            TextComponent textComponent = new TextComponent(plugin.getMyConfig().getReplacementOfMention(target));
            if (target.equalsIgnoreCase("everyone")) {
                notifEveryone(sender, textComponent.toLegacyText());
            }
            else if (Bukkit.getPlayer(target) != null) {
                notifPlayer(sender, textComponent.toLegacyText(), Bukkit.getPlayer(target));
            }
            else {
                return componentBuilder.create();
            }
            componentBuilder.append(textComponent);
        }
        return componentBuilder.create();
    }

    private static void notifEveryone(Player sender, String mention) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player != sender)
                sendTitleAndSubtitle(player, mention, plugin.getMessages().getMentionInfo(sender.getName()));
        }
    }

    private static void notifPlayer(Player sender, String mention, Player target) {
        sendTitleAndSubtitle(target, mention, plugin.getMessages().getMentionInfo(sender.getName()));
    }

    private static void sendTitleAndSubtitle(Player player, String title, String subtitle) {
        if (plugin.getMyConfig().isMentionNotificationSound())
            playMentionSound(player);
        player.sendTitle(title, subtitle, 10, 70, 20);
    }

    private static void playMentionSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.4f, 0.1f);
    }
}
