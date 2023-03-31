package br.com.shapeup.common.exceptions.user;

public class UserInvalidPasswordException extends RuntimeException {
    public UserInvalidPasswordException(String message) {
        super(message);
    }
}
