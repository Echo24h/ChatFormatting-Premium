package org.echo.chatformattingpremium;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.echo.chatformattingpremium.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String getFormatedMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<net.md_5.bungee.api.ChatColor> generateGradient(String color1, String color2, int length) {

        Color startColor = Color.fromRGB(Integer.parseInt(color1, 16));
        Color endColor = Color.fromRGB(Integer.parseInt(color2, 16));

        List<net.md_5.bungee.api.ChatColor> gradient = new ArrayList<>();
        for (int i = 0; i <= length; i++) {
            // Calculer les composantes RGB intermédiaires
            int r = interpolate(startColor.getRed(), endColor.getRed(), i, length);
            int g = interpolate(startColor.getGreen(), endColor.getGreen(), i, length);
            int b = interpolate(startColor.getBlue(), endColor.getBlue(), i, length);

            // Convertir les valeurs RGB en code hexadécimal
            String hexColor = String.format("#%02x%02x%02x", r, g, b);

            // Ajouter la couleur au dégradé
            gradient.add(ColorUtils.of(hexColor));
        }

        return gradient;
    }

    private static int interpolate(int start, int end, int currentStep, int totalSteps) {
        // Calculer la valeur interpolée entre les couleurs de départ et d'arrivée
        float ratio = (float) currentStep / totalSteps;
        int range = end - start;
        return Math.round(start + ratio * range);
    }
}
