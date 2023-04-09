package br.com.shapeup.core.domain.post;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class PostId extends Identifier<String> {
    private final String value;

    private PostId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static PostId unique() {
        return PostId.from(UUID.randomUUID());
    }

    public static PostId from(final String anId) {
        return new PostId(anId);
    }

    public static PostId from(final UUID anId) {
        return new PostId(anId.toString().toLowerCase());
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
        PostId PostId = (PostId) o;
        return getValue().equals(PostId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}


