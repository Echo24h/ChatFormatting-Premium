package org.echo.chatformattingpremium.format.formatters;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.Map;

public class ItemFormat_1_13 {

    public static TextComponent getItemHoverText(ItemStack itemStack, String itemName) {
        TextComponent hoverText;

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null && itemMeta.hasDisplayName()) {
            if (itemMeta.hasEnchants())
                hoverText = new TextComponent(ChatColor.AQUA + itemMeta.getDisplayName());
            else
                hoverText = new TextComponent(itemMeta.getDisplayName());
        }
        else {
            if (itemMeta != null && itemMeta.hasEnchants())
                hoverText = new TextComponent(ChatColor.AQUA + itemName);
            else
                hoverText = new TextComponent(itemName);
        }

        StringBuilder extraText = new StringBuilder();
        if (itemMeta != null && itemMeta.hasLore()) {
            extraText.append(ChatColor.WHITE + "\n" + String.join("\n", itemMeta.getLore()));
        }

        if (itemMeta != null && itemMeta.hasEnchants()) {
            extraText.append(ChatColor.GRAY + "\n" + getEnchantmentList(itemStack));
        }

        hoverText.addExtra( "" + extraText);

        return hoverText;
    }

    public static String getEnchantmentList(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        StringBuilder enchantmentList = new StringBuilder();

        if (itemMeta.hasEnchants()) {
            Map<Enchantment, Integer> enchantments = itemMeta.getEnchants();
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Enchantment enchantment = entry.getKey();
                int level = entry.getValue();
                enchantmentList.append(ItemFormat.formatTranslationKey(enchantment.getKey().getKey())).append(" ").append(toRomanNumeral(level)).append(" \n");
            }
            enchantmentList.setLength(enchantmentList.length() - 2); // Supprimer la dernière virgule et espace
        }

        return enchantmentList.toString();
    }

    private static String toRomanNumeral(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Number out of range (1-3999)");
        }

        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[number / 1000] +
                hundreds[(number % 1000) / 100] +
                tens[(number % 100) / 10] +
                units[number % 10];
    }
}
