package org.echo.chatformattingpremium.format;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.echo.chatformattingpremium.ChatFormatting;
import org.echo.chatformattingpremium.format.element.Element;
import org.echo.chatformattingpremium.format.element.ElementType;
import org.echo.chatformattingpremium.format.formatters.BadwordsFormat;

import java.util.List;

public class FormatManager {

    private ChatFormatting plugin = ChatFormatting.getInstance();

    private ElementsManager elementsManager = new ElementsManager();

    public BaseComponent[] formatPlayerChatMessage(Player player, String message) {

        // Bad words
        if (plugin.getBadWords().isEnable()) {
            message = BadwordsFormat.formatMessage(plugin, message);
        }

        // Convert message in List<Element>
        List<Element> extractedElements = elementsManager.extractElements(message);

        if (!checkPermissions(player, extractedElements))
            return null;

        // Format elements in List<Element>
        TextManager textManager = new TextManager();

        BaseComponent[] baseComponents = textManager.formatPlayerChatText(player, extractedElements);

        return baseComponents;
    }

    public BaseComponent[] formatText(String message) {

        if (message == null || message.isEmpty())
            return new ComponentBuilder("").create();

        TextManager textManager = new TextManager();
        return textManager.formatSimpleText(message);
    }

    private boolean checkPermissions(Player player, List<Element> elements) {

        // LINKS
        boolean containsLinkElement = elements.stream().anyMatch(element -> element.getType() == ElementType.LINK);
        if (containsLinkElement && !player.hasPermission("chatformatting.links")) {
            player.spigot().sendMessage(plugin.getMessages().getLinksPermission());
            return false;
        }

        // MENTIONS
        boolean containsMentionElement = elements.stream().anyMatch(element -> element.getType() == ElementType.MENTION);
        if (containsMentionElement) {
            for (Element element : elements) {
                if (element.getType() == ElementType.MENTION) {

                    if (!player.hasPermission("chatformatting.mention.*")) {
                        if (element.getValue().equalsIgnoreCase("@everyone")) {
                            if (!player.hasPermission("chatformatting.mention.everyone")) {
                                player.spigot().sendMessage(plugin.getMessages().getMentionPermission());
                                return false;
                            }
                        }
                        else {
                            if (!player.hasPermission("chatformatting.mention.player")) {
                                player.spigot().sendMessage(plugin.getMessages().getMentionPermission());
                                return false;
                            }
                        }
                    }
                }
            }
        }

        // ITEMS
        boolean containsItemElement = elements.stream().anyMatch(element -> element.getType() == ElementType.ITEM);
        if (containsItemElement && !player.hasPermission("chatformatting.item")) {
            player.spigot().sendMessage(plugin.getMessages().getItemPermission());
            return false;
        }

        return true;
    }
}
