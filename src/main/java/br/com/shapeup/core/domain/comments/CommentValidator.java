package br.com.shapeup.core.domain.comments;

import br.com.shapeup.common.exceptions.comment.InvalidCommentException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;
public class CommentValidator extends Validator {
    private final Comment comment;

    public CommentValidator(ValidationHandler handler, Comment comment) {
        super(handler);
        this.comment = comment;
    }

    public void validate() {
        validateCommentMessage();
        validateUserId();
        validatePostId();
    }

    private void validateCommentMessage() {
        if (comment.getCommentMessage().isEmpty()) {
            throw new InvalidCommentException("Comment message is empty");
        }
    }

    private void validateUserId() {
        if (comment.getUserId().isEmpty()) {
            throw new InvalidCommentException("User id is empty");
        }
    }

    private void validatePostId() {
        if (comment.getPostId().isEmpty()) {
            throw new InvalidCommentException("post id is empty");
        }
    }
}
