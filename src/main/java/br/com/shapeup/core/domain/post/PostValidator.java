package br.com.shapeup.core.domain.post;

import br.com.shapeup.common.exceptions.post.InvalidPostException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;
public class PostValidator extends Validator {
    private final Post post;
    protected PostValidator(ValidationHandler anHandler, Post post) {
        super(anHandler);
        this.post = post;
    }
    @Override
    public void validate() {
        validatePost();
    }
    public void validatePost() {
        checkPostConstraints();
        checkUserId();
    }
    private void checkPostConstraints() {
        String description = this.post.getDescription();
        if (description == null || description.isBlank()) {
            throw new InvalidPostException("'description' should not be null");
        }
    }

    private void checkUserId() {
        String userId = this.post.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new InvalidPostException("'userId' should not be null");
        }
    }
}
