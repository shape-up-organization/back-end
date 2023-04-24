package br.com.shapeup.common.exceptions.post;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String id) {
        super("Post " + id + " not found");
    }
}
