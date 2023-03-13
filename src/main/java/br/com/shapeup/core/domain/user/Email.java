package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;
import br.com.shapeup.core.domain.exceptions.user.valueobject.UserInvalidEmailException;

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

    public void validateEmail() {
        if(!value.contains("@")) {
            throw new UserInvalidEmailException("Email precisa conter @");
        }

        if(!value.contains(".")) {
            throw new UserInvalidEmailException("Email precisa conter .");
        }

        if(value.length() < 5) {
            throw new UserInvalidEmailException("Email precisa conter mais de 5 caracteres");
        }
    }
}
