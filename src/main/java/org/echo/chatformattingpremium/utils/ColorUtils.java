package org.echo.chatformattingpremium.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.echo.chatformattingpremium.ChatFormatting;

import java.awt.*;
public class ColorUtils {

    public static ChatColor of(String hexColor) {

        if (hexColor.length() > 0 & hexColor.charAt(0) == '#')
            hexColor = hexColor.substring(1);

        if (!ChatFormatting.getInstance().isMinecraftHigherVersion("1.16"))
            return ChatColor.getByChar(hexColor.toLowerCase().charAt(0));
        return ChatColor.of(hexToRgb(hexColor.toUpperCase()));
    }

    public static Color hexToRgb(String hexColor) {
        hexColor = hexColor.replace("#", ""); // Supprimer le caractère "#" s'il est présent
        int rgb = Integer.parseInt(hexColor, 16); // Convertir la valeur hexadécimale en entier

        int red = (rgb >> 16) & 0xFF; // Extraire la composante rouge
        int green = (rgb >> 8) & 0xFF; // Extraire la composante verte
        int blue = rgb & 0xFF; // Extraire la composante bleue

        return new Color(red, green, blue); // Créer et retourner un objet Color
    }
    /*public static ChatColor fromHex(String hexColor) {
        if (hexColor.startsWith("#")) {
            hexColor = hexColor.substring(1); // Supprimer le caractère "#" du début du code hexadécimal
        }

        // Vérifier si le code hexadécimal est valide (doit avoir une longueur de 6 caractères)
        if (hexColor.length() != 6) {
            return null;
        }

        // Convertir le code hexadécimal en minuscules
        hexColor = hexColor.toLowerCase();

        // Correspondance entre les codes hexadécimaux et les codes de couleurs de Bukkit
        Map<String, ChatColor> colorMap = new HashMap<>();
        colorMap.put("000000", ChatColor.BLACK);
        colorMap.put("0000aa", ChatColor.DARK_BLUE);
        colorMap.put("00aa00", ChatColor.DARK_GREEN);
        colorMap.put("00aaaa", ChatColor.DARK_AQUA);
        colorMap.put("aa0000", ChatColor.DARK_RED);
        colorMap.put("aa00aa", ChatColor.DARK_PURPLE);
        colorMap.put("ffaa00", ChatColor.GOLD);
        colorMap.put("aaaaaa", ChatColor.GRAY);
        colorMap.put("555555", ChatColor.DARK_GRAY);
        colorMap.put("55ff55", ChatColor.GREEN);
        colorMap.put("55ffff", ChatColor.AQUA);
        colorMap.put("ff5555", ChatColor.RED);
        colorMap.put("ff55ff", ChatColor.LIGHT_PURPLE);
        colorMap.put("ffff55", ChatColor.YELLOW);
        colorMap.put("ffffff", ChatColor.WHITE);

        // Vérifier si le code hexadécimal est présent dans la correspondance
        if (colorMap.containsKey(hexColor)) {
            return colorMap.get(hexColor);
        }

        // Convertir le code hexadécimal en composantes RGB
        int r = Integer.parseInt(hexColor.substring(0, 2), 16);
        int g = Integer.parseInt(hexColor.substring(2, 4), 16);
        int b = Integer.parseInt(hexColor.substring(4, 6), 16);

        // Calculer la distance entre les couleurs pour trouver la plus proche
        double minDistance = Double.MAX_VALUE;
        ChatColor closestColor = null;

        for (Map.Entry<String, ChatColor> entry : colorMap.entrySet()) {
            String colorHex = entry.getKey();
            int colorR = Integer.parseInt(colorHex.substring(0, 2), 16);
            int colorG = Integer.parseInt(colorHex.substring(2, 4), 16);
            int colorB = Integer.parseInt(colorHex.substring(4, 6), 16);

            // Calculer la distance entre les couleurs en utilisant la formule euclidienne
            double distance = Math.sqrt(Math.pow(r - colorR, 2) + Math.pow(g - colorG, 2) + Math.pow(b - colorB, 2));

            if (distance < minDistance) {
                minDistance = distance;
                closestColor = entry.getValue();
            }
        }

        return closestColor;
    }*/

    private static boolean isLegacy() {
        try {
            ChatColor.class.getMethod("of", String.class);
            return false;
        } catch (NoSuchMethodException e) {
            return true;
        }
    }

    public static boolean isSpecial(String color) {

        switch (color.charAt(0)) {
            case 'k':
                return true;
            case 'l':
                return true;
            case 'm':
                return true;
            case 'n':
                return true;
            case 'o':
                return true;
            case 'r':
                return true;
        }
        return false;
    }
}
