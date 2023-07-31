package org.echo.chatformattingpremium.format.formatters;

import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.echo.chatformattingpremium.ChatFormatting;

public class ItemFormat {

    public static BaseComponent[] formatItemInHand(Player player) {

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        ComponentBuilder formattedPart = new ComponentBuilder("");

        ItemMeta itemMeta = itemStack.getItemMeta();

        String itemId;
        ItemTag tag = null;
        if (itemMeta != null && ChatFormatting.getInstance().isMinecraftHigherVersion("1.18.2")) {
            tag = ItemTag.ofNbt(itemMeta.getAsString());
        }

        itemId = itemStack.getType().toString().toLowerCase();

        String itemName = "";
        if (itemMeta != null)
            itemName = itemMeta.getDisplayName();
        if (itemName == null || itemName.isEmpty())
            itemName = formatTranslationKey(itemId);
        itemName = new TextComponent(ChatFormatting.getInstance().getMyConfig().getReplacementOfItem(itemName)).toLegacyText();

        TextComponent itemComponent = new TextComponent(itemName);

        if (ChatFormatting.getInstance().isMinecraftHigherVersion("1.18.2")) {
            Item item = new Item(itemId
                    .replace("item.", "")
                    .replace("minecraft.", "")
                    .replace("block.", ""), 1, tag);
            itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, item));
        } else {
            TextComponent itemHoverText = ItemFormat_1_13.getItemHoverText(itemStack, formatTranslationKey(itemId));
            itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[] {itemHoverText}));
        }

        formattedPart.append(new ComponentBuilder(itemComponent).create()).append(new TextComponent(""), ComponentBuilder.FormatRetention.NONE);

        return formattedPart.create();
    }

    public static String formatTranslationKey(String input) {
        input = input.replace("item.", "")
                .replace("minecraft.", "")
                .replace("block.", "")
                .replace("_", " ");
        return toTitleCase(input);
    }

    private static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c) || c == '_') {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }
            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
