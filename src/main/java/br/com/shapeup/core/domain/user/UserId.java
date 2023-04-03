package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class UserId extends Identifier<String> {
    private final String value;

    private UserId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static UserId unique() {
        return UserId.from(UUID.randomUUID());
    }

    public static UserId from(final String anId) {
        return new UserId(anId);
    }

    public static UserId from(final UUID anId) {
        return new UserId(anId.toString().toLowerCase());
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
        UserId userId = (UserId) o;
        return getValue().equals(userId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
