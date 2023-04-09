package br.com.shapeup.core.domain.post;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class PostPhotoId extends Identifier<String> {
    private final String value;

    private PostPhotoId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static PostPhotoId unique() {
        return PostPhotoId.from(UUID.randomUUID());
    }

    public static PostPhotoId from(final String anId) {
        return new PostPhotoId(anId);
    }

    public static PostPhotoId from(final UUID anId) {
        return new PostPhotoId(anId.toString().toLowerCase());
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
        PostId postPhotoId = (PostId) o;
        return getValue().equals(postPhotoId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}


