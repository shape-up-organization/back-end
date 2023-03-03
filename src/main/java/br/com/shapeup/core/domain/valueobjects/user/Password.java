package br.com.shapeup.core.domain.valueobjects.user;

public class Password {
    private String value;

    public Password(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
