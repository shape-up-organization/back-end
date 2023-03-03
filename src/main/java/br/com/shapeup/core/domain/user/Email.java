package br.com.shapeup.core.domain.user;

public class Email {
    private String value;

    public Email(String value) {
        this.value = value;
    }

    Email createEmail(String email) {
        return new Email(email);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
