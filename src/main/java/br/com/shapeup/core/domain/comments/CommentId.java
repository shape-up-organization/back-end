package br.com.shapeup.core.domain.comments;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class CommentId extends Identifier<String> {
    private final String value;

    private CommentId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CommentId unique() {
        return CommentId.from(UUID.randomUUID());
    }

    public static CommentId from(final String anId) {
        return new CommentId(anId);
    }

    public static CommentId from(final UUID anId) {
        return new CommentId(anId.toString().toLowerCase());
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
        CommentId commentId= (CommentId) o;
        return getValue().equals(commentId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
