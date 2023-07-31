package org.echo.chatformattingpremium.format.element;

public class Element {

    private ElementType type;
    private String value;

    public Element(ElementType type, String value) {
        this.type = type;
        this.value = value;
    }

    public void setType(ElementType elementType) {
        this.type = elementType;
    }

    public void setValue(String elementType) {
        this.value = value;
    }

    public ElementType getType() {
        return type;
    }
    public String getValue() {
        return value;
    }
}