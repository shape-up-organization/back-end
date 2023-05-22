package br.com.shapeup.core.domain.post;

import br.com.shapeup.common.exceptions.post.InvalidPostPhotoException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;

public class PostPhotoValidator extends Validator {
    private final PostPhoto postPhoto;

    protected PostPhotoValidator(ValidationHandler anHandler, PostPhoto postPhoto) {
        super(anHandler);
        this.postPhoto = postPhoto;
    }

    @Override
    public void validate() {
        validatePostPhoto();
    }

    public void validatePostPhoto() {
        checkPostPhotoConstraints();
    }

    private void checkPostPhotoConstraints() {
        String idPost = this.postPhoto.getIdPost();
        if (idPost == null || idPost.isBlank()) {
            throw new InvalidPostPhotoException("'idPost' should not be null");
        }

        String photoUrlPhoto = this.postPhoto.getPhotoUrlPhoto();
        if (photoUrlPhoto == null || photoUrlPhoto.isBlank()) {
            throw new InvalidPostPhotoException("'photoUrlPhoto' should not be null");
        }
    }
}
