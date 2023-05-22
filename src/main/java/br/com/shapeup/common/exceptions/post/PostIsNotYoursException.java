package br.com.shapeup.common.exceptions.post;

public class PostIsNotYoursException extends RuntimeException {
    public PostIsNotYoursException(String postId) {
        super("Post %s is not yours".formatted(postId));
    }
}
