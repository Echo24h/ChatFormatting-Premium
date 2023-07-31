package org.echo.chatformattingpremium.format;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.echo.chatformattingpremium.ChatFormatting;
import org.echo.chatformattingpremium.format.element.Element;
import org.echo.chatformattingpremium.format.element.ElementPattern;
import org.echo.chatformattingpremium.format.element.ElementType;
import org.echo.chatformattingpremium.format.formatters.ItemFormat;
import org.echo.chatformattingpremium.format.formatters.LinksFormat;
import org.echo.chatformattingpremium.format.formatters.MentionFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElementsManager {

    private static ChatFormatting plugin = ChatFormatting.getInstance();

    public static BaseComponent[] getFormattedElement(Player player, Element element) {

        switch (element.getType()) {
            case LINK:
                return LinksFormat.formatUrl(element.getValue(), plugin.getMyConfig().getReplacementOfLink());
            case ITEM:
                return ItemFormat.formatItemInHand(player);
            case MENTION:
                return MentionFormat.formatMention(player, element.getValue());
            default:
                return new ComponentBuilder(element.getValue()).create();
        }
    }

    public List<Element> extractElements(String message) {

        List<Element> elements = new ArrayList<>();

        elements.add(new Element(ElementType.UNKNOWN, message));
        elements = extract(elements, ElementType.LINK);
        elements = extract(elements, ElementType.MENTION);
        elements = extract(elements, ElementType.ITEM);
        elements = setUnknownToText(elements);
        return elements;
    }

    private List<Element> extract(List<Element> elements, ElementType patternType) {
        List<Element> newElements = new ArrayList<>();

        for (Element element : elements) {
            if (element.getType() == ElementType.UNKNOWN) {
                List<Element> extractedElements = extractSingleType(element, patternType);
                newElements.addAll(extractedElements);
            }
            else {
                newElements.add(element);
            }
        }
        return newElements;
    }

    private List<Element> extractSingleType(Element element, ElementType patternType) {
        List<Element> extractedElements = new ArrayList<>();

        String value = element.getValue();

        Pattern pattern = ElementPattern.get(patternType);

        Matcher matcher = pattern.matcher(value);
        int lastEndIndex = 0;

        while (matcher.find()) {
            int startIndex = matcher.start();
            int endIndex = matcher.end();

            if (startIndex > lastEndIndex) {
                extractedElements.add(new Element(ElementType.UNKNOWN, value.substring(lastEndIndex, startIndex)));
            }

            String extractedValue = value.substring(startIndex, endIndex);
            extractedElements.add(new Element(patternType, extractedValue));

            lastEndIndex = endIndex;
        }

        if (lastEndIndex < value.length()) {
            extractedElements.add(new Element(ElementType.UNKNOWN, value.substring(lastEndIndex)));
        }

        return extractedElements;
    }

    private List<Element> setUnknownToText(List<Element> elements) {
        for (Element element : elements) {
            if (element.getType() ==ElementType.UNKNOWN)
                element.setType(ElementType.TEXT);
        }
        return elements;
    }

}
