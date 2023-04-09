package br.com.shapeup.core.domain.post;

import br.com.shapeup.common.exceptions.post.InvalidPostLikeException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;
public class PostLikeValidator extends Validator {
    private final PostLike postLike;
    protected PostLikeValidator(ValidationHandler anHandler, PostLike postLike) {
        super(anHandler);
        this.postLike = postLike;
    }
    @Override
    public void validate() {
        validatePostLike();
    }
    public void validatePostLike() {
        checkPostLikeConstraints();
    }
    private void checkPostLikeConstraints() {
        String idPost = this.postLike.getIdPost();
        if (idPost == null || idPost.isBlank()) {
            throw new InvalidPostLikeException("'idPost' should not be null");
        }

        String usernameLiked = this.postLike.getUsernameLiked();
        if (usernameLiked == null || usernameLiked.isBlank()) {
            throw new InvalidPostLikeException("'usernameLiked' should not be null");
        }
    }
}
