package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;

public class Email extends ValueObject {
    private String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email create(String email) {
        return new Email(email);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
