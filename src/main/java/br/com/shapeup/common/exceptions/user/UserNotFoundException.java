package br.com.shapeup.common.exceptions.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userCredential) {
        super(String.format("User with credential: %s not found.", userCredential));
    }
}
