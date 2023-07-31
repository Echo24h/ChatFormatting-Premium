package org.echo.chatformattingpremium.format.formatters;

import org.echo.chatformattingpremium.ChatFormatting;

public class BadwordsFormat {

    public static String formatMessage(ChatFormatting plugin, String message) {
        if (plugin.getBadWords().isUltraFiltering())
            return formatMessageUltra(plugin, message);
        else
            return formatMessageMin(plugin, message);
    }

    public static String formatMessageMin(ChatFormatting plugin, String message) {
        for (String badWord : plugin.getBadWords().getBadWords()) {
            message = message.replace(badWord, plugin.getBadWords().getBadWordsReplacement());
        }
        return message;
    }


    public static String formatMessageUltra(ChatFormatting plugin, String message) {
        for (String badWord : plugin.getBadWords().getBadWords()) {
            StringBuilder patternBuilder = new StringBuilder();
            for (int i = 0; i < badWord.length(); i++) {
                patternBuilder.append("(").append(badWord.charAt(i)).append(")+");
            }
            String pattern = patternBuilder.toString();
            message = message.replaceAll(pattern, plugin.getBadWords().getBadWordsReplacement());
        }
        return message;
    }
}
