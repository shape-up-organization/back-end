package br.com.shapeup.core.domain.comments;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

import java.util.Objects;

public class Comment extends Entity<CommentId> {
    private String commentMessage;

    public Comment( String commentMessage) {
        super(CommentId.unique());
        this.commentMessage = commentMessage;
    }

    public static Comment newComment(String commentMessage){
        return newComment(commentMessage);
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        return Objects.equals(commentMessage, comment.commentMessage);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (commentMessage != null ? commentMessage.hashCode() : 0);
        return result;
    }
}
