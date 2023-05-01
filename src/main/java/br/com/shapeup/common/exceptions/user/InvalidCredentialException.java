package br.com.shapeup.common.exceptions.user;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException() {
        super("User credentials are not valid");
    }
}
