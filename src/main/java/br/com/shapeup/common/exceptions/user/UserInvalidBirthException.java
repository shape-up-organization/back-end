package br.com.shapeup.common.exceptions.user;

public class UserInvalidBirthException extends RuntimeException {
    public UserInvalidBirthException() {
        super("invalid birth!");
    }

    public UserInvalidBirthException(String message) {
        super(message);
    }
}
