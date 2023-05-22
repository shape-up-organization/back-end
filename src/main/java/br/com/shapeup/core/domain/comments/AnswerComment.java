package br.com.shapeup.core.domain.comments;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.Objects;

public class AnswerComment extends Entity<AnswerCommentId> {
    private String userId;

    private String commentId;

    private String commentMessage;

    private AnswerComment(AnswerCommentId id, String commentMessage, String userId, String commentId) {
        super(id);
        this.commentMessage = commentMessage;
        this.userId = userId;
        this.commentId = commentId;
    }
     public static AnswerComment newAnswerComment(AnswerCommentId id ,String commentMessage, String userId, String commentId){
        return new AnswerComment(id, commentMessage, userId, commentId);
     }

    @Override
    public void validate(ValidationHandler handler) {
        new AnswerCommentValidator(handler, this).validate();
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AnswerComment that = (AnswerComment) o;

        return Objects.equals(userId, that.userId) &&
                Objects.equals(commentId, that.commentId) &&
                Objects.equals(commentMessage, that.commentMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserId(), getCommentId(), getCommentMessage());
    }
}
