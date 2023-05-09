package br.com.shapeup.common.exceptions.comment;

public class CommentIsNotYours extends RuntimeException {
    public CommentIsNotYours(String commentId) {
        super("Comment %s is not yours".formatted(commentId));
    }
}
