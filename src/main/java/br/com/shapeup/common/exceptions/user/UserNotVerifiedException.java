package br.com.shapeup.common.exceptions.user;

public class UserNotVerifiedException extends RuntimeException{
    public UserNotVerifiedException(String message) {
        super(message);
    }
}
