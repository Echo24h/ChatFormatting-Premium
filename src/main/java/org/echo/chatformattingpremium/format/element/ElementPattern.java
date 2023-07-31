package org.echo.chatformattingpremium.format.element;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ElementPattern {
    private static final Map<ElementType, Pattern> patterns = new HashMap<>();

    static {
        // Ajoutez les patterns pour chaque type d'élément dans la liste
        addPatternForElementType(ElementType.LINK, "(?i)((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]|\\b))");
        addPatternForElementType(ElementType.MENTION, "@[a-zA-Z0-9_]+");
        addPatternForElementType(ElementType.ITEM, "\\[item\\]");
        //addPatternForElementType(ElementType.COLOR, "&[^&]*");
        // Ajoutez d'autres patterns ici pour les autres types d'éléments
    }

    private static void addPatternForElementType(ElementType elementType, String regexPattern) {
        Pattern pattern = Pattern.compile(regexPattern);
        patterns.put(elementType, pattern);
    }

    public static Pattern get(ElementType elementType) {
        return patterns.get(elementType);
    }
}
