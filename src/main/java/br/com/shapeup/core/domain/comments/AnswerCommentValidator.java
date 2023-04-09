package br.com.shapeup.core.domain.comments;

import br.com.shapeup.common.exceptions.comment.InvalidCommentException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;

public class AnswerCommentValidator extends Validator {
    private final AnswerComment answerComment;

    public AnswerCommentValidator(ValidationHandler handler, AnswerComment answerComment) {
        super(handler);
        this.answerComment = answerComment;
    }

    public void validate() {
        validateCommentMessage();
        validateUserId();
        validateCommentId();
    }

    private void validateCommentMessage() {
        if (answerComment.getCommentMessage().isEmpty()) {
            throw new InvalidCommentException("Comment message is empty");
        }
    }

    private void validateUserId() {
        if (answerComment.getUserId().isEmpty()) {
            throw new InvalidCommentException("User id is empty");
        }
    }

    private void validateCommentId() {
        if (answerComment.getCommentId().isEmpty()) {
            throw new InvalidCommentException("Comment id is empty");
        }
    }
}
