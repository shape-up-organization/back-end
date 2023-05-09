package br.com.shapeup.common.exceptions.comment;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String idPost) {
        super("Comment %s not found".formatted(idPost));
    }
}
