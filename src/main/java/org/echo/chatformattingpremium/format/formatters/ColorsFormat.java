package org.echo.chatformattingpremium.format.formatters;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.echo.chatformattingpremium.config.Colors;
import org.echo.chatformattingpremium.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

public class ColorsFormat {

    public static List<ChatColor> generateGradient(String color1, String color2, int length) {

        // Convertir les couleurs de départ et d'arrivée en objets Color
        Color startColor = Color.fromRGB(Integer.parseInt(color1, 16));
        Color endColor = Color.fromRGB(Integer.parseInt(color2, 16));

        List<ChatColor> gradient = new ArrayList<>();
        // Calculer les valeurs de couleur intermédiaires
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
