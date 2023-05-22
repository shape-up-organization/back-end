package br.com.shapeup.core.domain.post;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.Objects;

public class Post extends Entity<PostId> {
    private String userId;

    private String description;

    private Post(PostId id, String description, String userId) {
        super(id);
        this.description = description;
        this.userId = userId;
    }

    public static Post newPost(PostId id,String description, String userId){
        return new Post(id, description, userId);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new PostValidator(handler, this).validate();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Post post = (Post) o;
        return Objects.equals(userId, post.userId) && Objects.equals(description, post.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, description);
    }
}
