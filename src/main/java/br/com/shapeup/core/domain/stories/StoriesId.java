package br.com.shapeup.core.domain.stories;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class StoriesId extends Identifier {
    private final String value;

    private StoriesId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static StoriesId unique() {
        return StoriesId.from(UUID.randomUUID());
    }

    public static StoriesId from(final String anId) {
        return new StoriesId(anId);
    }

    public static StoriesId from(final UUID anId) {
        return new StoriesId(anId.toString().toLowerCase());
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
        StoriesId storiesId = (StoriesId) o;
        return getValue().equals(storiesId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

