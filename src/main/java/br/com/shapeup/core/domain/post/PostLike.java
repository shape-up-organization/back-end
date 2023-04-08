package br.com.shapeup.core.domain.post;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

public class PostLike extends Entity<PostLikeId> {
    private boolean isLiked;

    public PostLike( boolean isLiked) {
        super(PostLikeId.unique());
        this.isLiked = isLiked;
    }
    public static PostLike newPostLike(boolean isLiked){
        return new PostLike(isLiked);
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PostLike postLike = (PostLike) o;

        return isLiked == postLike.isLiked;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isLiked ? 1 : 0);
        return result;
    }
}
