package br.com.shapeup.common.exceptions;

public class ShapeUpBaseException extends RuntimeException {
    private String message;
    private Throwable cause = null;
    public ShapeUpBaseException(String message) {
        super(message);
    }

    public ShapeUpBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
