package br.com.shapeup.core.domain.post;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class PostLikeId extends Identifier<String> {
    private final String value;

    private PostLikeId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static PostLikeId unique() {
        return PostLikeId.from(UUID.randomUUID());
    }

    public static PostLikeId from(final String anId) {
        return new PostLikeId(anId);
    }

    public static PostLikeId from(final UUID anId) {
        return new PostLikeId(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PostLikeId postLikeId = (PostLikeId) o;
        return getValue().equals(postLikeId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}


