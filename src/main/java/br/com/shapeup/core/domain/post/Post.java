package br.com.shapeup.core.domain.post;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;

import java.util.Objects;

public class Post extends Entity<PostId> {
    private String description;

    public Post( String description) {
        super(PostId.unique());
        this.description = description;
    }

    public static Post newPost(String description){
        return new Post(description);
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Post post = (Post) o;

        return Objects.equals(description, post.description);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
