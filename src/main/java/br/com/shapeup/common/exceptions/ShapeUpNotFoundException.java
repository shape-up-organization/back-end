package br.com.shapeup.common.exceptions;

public class ShapeUpNotFoundException extends RuntimeException {
    public ShapeUpNotFoundException(String message) {
        super(message);
    }
}
