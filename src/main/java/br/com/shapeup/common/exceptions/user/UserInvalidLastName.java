package br.com.shapeup.common.exceptions.user;

public class UserInvalidLastName extends RuntimeException{
    public UserInvalidLastName(String message) {
        super(message);
    }
}
