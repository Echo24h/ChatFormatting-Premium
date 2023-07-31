package org.echo.chatformattingpremium.format.formatters;

import net.md_5.bungee.api.chat.*;
import org.echo.chatformattingpremium.ChatFormatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinksFormat {

    private static Pattern URL_PATTERN = Pattern.compile("(?i)((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]|\\b))");

    private static ChatFormatting plugin = ChatFormatting.getInstance();

    public static BaseComponent[] formatUrl(String url, String replacement) {

        // Vérifier si le lien commence par "http://" ou "https://"
        if (!url.matches("^(https?|ftp)://.*$")) {
            url = "https://" + url;
        }

        ComponentBuilder formattedPart = new ComponentBuilder("");

        BaseComponent[] replacementFormatted = plugin.getFormatManager().formatText(replacement);
        TextComponent linkComponent = new TextComponent(replacementFormatted);
        linkComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        linkComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, plugin.getMessages().getLinksInfo(url)));

        formattedPart.append(linkComponent);

        return formattedPart.create();
    }

    public static Pattern getUrlPattern() { return URL_PATTERN; }

    public static boolean containsUrl(String message) {
        Matcher matcher = LinksFormat.getUrlPattern().matcher(message);
        return matcher.find();
    }
}
