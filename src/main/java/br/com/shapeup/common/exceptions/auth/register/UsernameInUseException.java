package br.com.shapeup.common.exceptions.auth.register;

public class UsernameInUseException extends RuntimeException {
    public UsernameInUseException(String message) {
        super(message);
    }
}
