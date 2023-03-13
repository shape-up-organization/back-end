package br.com.shapeup.core.domain.exceptions.user.valueobject;

public class UserInvalidEmailException extends RuntimeException {
    public UserInvalidEmailException() {
        super("Email inválido");
    }

    public UserInvalidEmailException(String message) {
        super(message);
    }
}
