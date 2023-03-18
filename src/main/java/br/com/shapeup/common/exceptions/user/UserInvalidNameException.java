package br.com.shapeup.common.exceptions.user;

public class UserInvalidNameException extends RuntimeException{
    public UserInvalidNameException(String message) {
        super(message);
    }
}
