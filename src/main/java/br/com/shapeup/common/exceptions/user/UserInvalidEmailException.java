package br.com.shapeup.common.exceptions.user;

public class UserInvalidEmailException extends RuntimeException {
    public UserInvalidEmailException(String message) {
        super(message);
    }
}
