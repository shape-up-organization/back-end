package br.com.shapeup.core.domain.verification.exception;

public class ExpiratedCodeException extends RuntimeException {

    public ExpiratedCodeException(String message) {
        super(message);
    }
}