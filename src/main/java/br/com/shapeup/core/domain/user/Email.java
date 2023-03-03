package br.com.shapeup.core.domain.user;

public class Email {
    private String value;

    public Email(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
