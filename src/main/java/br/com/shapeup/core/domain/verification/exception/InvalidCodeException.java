package br.com.shapeup.core.domain.verification.exception;

public class InvalidCodeException extends RuntimeException {

    public InvalidCodeException(String message) {
        super(message);
    }
}
