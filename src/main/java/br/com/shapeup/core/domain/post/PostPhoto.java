package br.com.shapeup.core.domain.post;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.Objects;

public class PostPhoto extends Entity<PostPhotoId> {
    private String idPost;

    private String photoUrlPhoto;

    public PostPhoto(PostPhotoId id,String photoUrlPhoto, String idPost) {
        super(id);
        this.photoUrlPhoto = photoUrlPhoto;
        this.idPost = idPost;
    }
    public static PostPhoto newPostPhoto(PostPhotoId id, String photoUrlPhoto, String idPost){
        return new PostPhoto(id, photoUrlPhoto, idPost);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new PostPhotoValidator(handler, this).validate();
    }

    public String getPhotoUrlPhoto() {
        return photoUrlPhoto;
    }

    public void setPhotoUrlPhoto(String photoUrlPhoto) {
        this.photoUrlPhoto = photoUrlPhoto;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        PostPhoto postPhoto = (PostPhoto) o;
        return Objects.equals(idPost, postPhoto.idPost) &&
                Objects.equals(photoUrlPhoto, postPhoto.photoUrlPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idPost, photoUrlPhoto);
    }
}
