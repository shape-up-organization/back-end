package br.com.shapeup.core.domain.comments;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.Objects;

public class Comment extends Entity<CommentId> {
    private String userId;

    private String postId;

    private String commentMessage;

    private Comment(CommentId id,String commentMessage, String userId, String postId) {
        super(id);
        this.commentMessage = commentMessage;
        this.postId = postId;
        this.userId = userId;
    }

    public static Comment newComment(CommentId id,String commentMessage, String userId, String postId){
        return new Comment(id, commentMessage, userId, postId);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CommentValidator(handler, this).validate();
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(userId, comment.userId) && Objects.equals(postId, comment.postId) && Objects.equals(commentMessage, comment.commentMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, postId, commentMessage);
    }
}
