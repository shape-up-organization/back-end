package br.com.shapeup.core.domain.post;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.Objects;

public class PostLike extends Entity<PostLikeId> {
    private String idPost;

    private String usernameLiked;

    private PostLike(PostLikeId id, String idPost, String usernameLiked) {
        super(id);
        this.idPost = idPost;
        this.usernameLiked = usernameLiked;
    }

    public static PostLike newPostLike(PostLikeId id,String idPost, String usernameLiked){
        return new PostLike(id, idPost, usernameLiked);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new PostLikeValidator(handler, this).validate();
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getUsernameLiked() {
        return usernameLiked;
    }

    public void setUsernameLiked(String usernameLiked) {
        this.usernameLiked = usernameLiked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PostLike postLike = (PostLike) o;

        return Objects.equals(idPost, postLike.idPost) &&
                Objects.equals(usernameLiked, postLike.usernameLiked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idPost, usernameLiked);
    }
}
