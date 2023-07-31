package org.echo.chatformattingpremium.format;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.echo.chatformattingpremium.ChatFormatting;
import org.echo.chatformattingpremium.config.Colors;
import org.echo.chatformattingpremium.format.element.Element;
import org.echo.chatformattingpremium.format.element.ElementType;
import org.echo.chatformattingpremium.format.formatters.ColorsFormat;
import org.echo.chatformattingpremium.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

public class TextManager {

    private ChatFormatting plugin = ChatFormatting.getInstance();

    // Déclaration des boolean en tant que private static dans la classe
    private int gradiantIndex = 0;
    private List<ChatColor> gradiant = null;
    private ChatColor previousColor = ChatColor.WHITE;
    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUnderlined = false;
    private boolean isStrikethrough = false;
    private boolean isObfuscated = false;

    public TextManager() { return ; }

    public BaseComponent[] formatPlayerChatText(Player player, List<Element> elements) {
        ComponentBuilder formattedElements = new ComponentBuilder("");

        // DISPLAY NAME
        formattedElements.append(plugin.getDisplayNames().getDisplayNameChat(player));

        // CHAT TEXT
        for (int i = 0; i < elements.size(); i++) {

            Element element = elements.get(i);
            if (element.getType() == ElementType.TEXT) {
                // Format single TEXT element
                BaseComponent[] formattedValue = formatTargetedElement(elements, i);
                formattedElements.append(formattedValue);
            } else {
                // For non-TEXT elements, just add them to the list without formatting
                formattedElements
                        .append(ElementsManager.getFormattedElement(player, element), ComponentBuilder.FormatRetention.NONE)
                        .append("", ComponentBuilder.FormatRetention.FORMATTING);
            }
        }
        // Réinitialisation des boolean à false après avoir formaté tous les TEXT
        resetFormattingFlags();
        return formattedElements.create();
    }

    public BaseComponent[] formatSimpleText(String message) {
        ComponentBuilder formattedElements = new ComponentBuilder("");

        List<Element> elements = new ArrayList<>();
        elements.add(new Element(ElementType.TEXT, message));

        for (int i = 0; i < elements.size(); i++) {

            Element element = elements.get(i);
            if (element.getType() == ElementType.TEXT) {
                // Format single TEXT element
                BaseComponent[] formattedValue = formatTargetedElement(elements, i);
                formattedElements.append(formattedValue);
            }
        }
        return formattedElements.create();
    }

    private BaseComponent[] formatTargetedElement(List<Element> elements, int elementIndex) {

        ComponentBuilder formattedValue = new ComponentBuilder("");
        setPrevFormatting(formattedValue);

        String value = elements.get(elementIndex).getValue();

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '&') {
                if (i + 1 < value.length()) {
                    char nextChar = value.charAt(i + 1);
                    String colorText = value.substring(i + 1);

                    String colorCode = Colors.isGradiantColor(colorText);
                    if (colorCode != null) {
                        setGradiant(elements, elementIndex, 1 + i + colorCode.length(), colorCode);
                        i += (colorCode.length());
                        continue;
                    }
                    colorCode = Colors.isColor(colorText);
                    if (colorCode != null) {
                        previousColor = Colors.getColor(colorCode);
                        i += colorCode.length(); // Skip the next character as it was the color code
                        continue;
                    } else {
                        appendFormattingCode(nextChar, formattedValue);
                        i++; // Skip the next character as it was the format code
                        continue;
                    }
                }
            }
            if (gradiant != null)
                appendGradiant(formattedValue, c);
            else
                formattedValue.append(String.valueOf(c)).color(previousColor);;
        }
        return formattedValue.create();
    }

    private void appendGradiant(ComponentBuilder formattedValue, char c) {

        if (gradiantIndex < gradiant.size()) {
            ComponentBuilder componentBuilder = new ComponentBuilder(String.valueOf(c));
            componentBuilder.color(gradiant.get(gradiantIndex));
            formattedValue.append(componentBuilder.create());
        }
        gradiantIndex++;
        if (gradiantIndex >= gradiant.size()) {
            gradiant = null;
            gradiantIndex = 0;
        }
    }

    private void setPrevFormatting(ComponentBuilder formattedValue) {
        if (isBold)
            formattedValue.bold(true);
        if (isItalic)
            formattedValue.italic(true);
        if (isUnderlined)
            formattedValue.underlined(true);
        if (isStrikethrough)
            formattedValue.strikethrough(true);
        if (isObfuscated)
            formattedValue.obfuscated(true);
    }

    private void appendFormattingCode(char formatCode, ComponentBuilder formattedValue) {

        ComponentBuilder componentBuilder = new ComponentBuilder("");

        switch (formatCode) {
            case 'l':
                if (isBold == false)
                    isBold = true;
                componentBuilder.bold(true);
                break;
            case 'o':
                if (isItalic == false)
                    isItalic = true;
                componentBuilder.italic(true);
                break;
            case 'n':
                if (isUnderlined == false)
                    isUnderlined = true;
                componentBuilder.underlined(true);
                break;
            case 'm':
                if (isStrikethrough == false)
                    isStrikethrough = true;
                componentBuilder.strikethrough(true);
                break;
            case 'k':
                if (isObfuscated == false)
                    isObfuscated = true;
                componentBuilder.obfuscated(true);
                break;
            case 'r':
                resetFormattingFlags();
                componentBuilder.bold(false);
                componentBuilder.italic(false);
                componentBuilder.underlined(false);
                componentBuilder.strikethrough(false);
                componentBuilder.obfuscated(false);
                formattedValue.append(componentBuilder.color(ChatColor.RESET).create());
                return;
            default:
                formattedValue.append("&");
                return;
        }
        formattedValue.append(componentBuilder.create());
    }

    private void resetFormattingFlags() {
        isBold = false;
        isItalic = false;
        isUnderlined = false;
        isStrikethrough = false;
        isObfuscated = false;
        gradiant = null;
        gradiantIndex = 0;
        previousColor = ChatColor.WHITE;
    }

    private List<ChatColor> setGradiant(List<Element> elements, int elementIndex, int valueIndex, String colorCode) {

        int size = getGradiantSize(elements, elementIndex, valueIndex);
        Colors.GradiantColor colors = Colors.getGradiantColor(colorCode);
        gradiant = ColorsFormat.generateGradient(colors.getColor1(), colors.getColor2(), size);
        gradiantIndex = 0;
        return gradiant;
    }

    private boolean isGradiantReset(String colorMessage) {

        if (colorMessage.charAt(0) == 'r')
            return true;
        if (Colors.isGradiantColor(colorMessage) != null)
            return true;
        if (Colors.isColor(colorMessage) != null)
            return true;
        return false;
    }

    private int getGradiantSize(List<Element> elements, int elementIndex, int valueIndex) {
        int size = 0;

        while (elementIndex < elements.size()) {

            Element element = elements.get(elementIndex);
            String value = element.getValue();

            if (element.getType() == ElementType.TEXT) {
                while (valueIndex < value.length()) {
                    char c = value.charAt(valueIndex);
                    if (c == '&') {
                        if (valueIndex + 1 < value.length()) {
                            if (isGradiantReset(value.substring(valueIndex + 1)))
                                return size;
                            if (ColorUtils.isSpecial(value.substring(valueIndex + 1)))
                                valueIndex++; size--;
                        }
                    }
                    size++;
                    valueIndex++;
                }
                valueIndex = 0;
            }
            elementIndex++;
        }
        return size;
    }
}
