package br.com.shapeup.common.exceptions.post;

public class InvalidPostLikeException extends RuntimeException {
    public InvalidPostLikeException(String message) {
        super(message);
    }
}
