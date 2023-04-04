package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;
import br.com.shapeup.common.exceptions.user.UserInvalidPasswordException;

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

    public static void validatePassword(String password) {
        if (password.length() < 8) {
            throw new UserInvalidPasswordException("password should contain more than 8 characters");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new UserInvalidPasswordException("password should contain at least one uppercase letter");
        }

        if(!password.matches(".*[a-z].*")) {
            throw new UserInvalidPasswordException("password should contain at least one lowercase letter");
        }

        if (!password.matches(".*[0-9].*")) {
            throw new UserInvalidPasswordException("password should contain at least one number");
        }

        if(!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new UserInvalidPasswordException("password should contain at least one special character");
        }
    }
}
