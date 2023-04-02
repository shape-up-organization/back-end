package br.com.shapeup.core.domain.post;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

import java.util.Objects;

public class PostPhoto extends Entity<PostPhotoId> {
    private String photoUrlPhoto;

    public PostPhoto( String photoUrlPhoto) {
        super(PostPhotoId.unique());
        this.photoUrlPhoto = photoUrlPhoto;
    }
    public static PostPhoto newPostPhoto(String photoUrlPhoto){
        return new PostPhoto(photoUrlPhoto);
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public String getPhotoUrlPhoto() {
        return photoUrlPhoto;
    }

    public void setPhotoUrlPhoto(String photoUrlPhoto) {
        this.photoUrlPhoto = photoUrlPhoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PostPhoto postPhoto = (PostPhoto) o;

        return Objects.equals(photoUrlPhoto, postPhoto.photoUrlPhoto);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (photoUrlPhoto != null ? photoUrlPhoto.hashCode() : 0);
        return result;
    }
}
