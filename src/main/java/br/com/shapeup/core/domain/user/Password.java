package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;

public class Password extends ValueObject {
    private String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password create(String password) {
        return new Password(password);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
