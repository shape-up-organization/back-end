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

    public void validatePassword() {
        if (value.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }

        if (!value.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
    }
}
