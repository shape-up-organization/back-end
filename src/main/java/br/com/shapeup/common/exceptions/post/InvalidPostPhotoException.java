package br.com.shapeup.common.exceptions.post;

public class InvalidPostPhotoException extends RuntimeException {
    public InvalidPostPhotoException(String message) {
        super(message);
    }
}
