package br.com.shapeup.core.domain.comments;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

import java.util.Objects;

public class AnswerComment extends Entity<AnswerCommentId> {
    private String commentMessage;

    public AnswerComment( String commentMessage) {
        super(AnswerCommentId.unique());
        this.commentMessage = commentMessage;
    }
     public static AnswerComment newAnswerComment(String commentMessage){
        return newAnswerComment(commentMessage);
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

        AnswerComment that = (AnswerComment) o;

        return Objects.equals(commentMessage, that.commentMessage);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (commentMessage != null ? commentMessage.hashCode() : 0);
        return result;
    }
}
