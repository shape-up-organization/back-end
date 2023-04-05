package br.com.shapeup.core.domain.friend;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class FriendshipRequestId extends Identifier<String> {
    private final String value;

    private FriendshipRequestId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static FriendshipRequestId unique() {
        return FriendshipRequestId.from(UUID.randomUUID());
    }

    public static FriendshipRequestId from(final String anId) {
        return new FriendshipRequestId(anId);
    }

    public static FriendshipRequestId from(final UUID anId) {
        return new FriendshipRequestId(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FriendshipRequestId that = (FriendshipRequestId) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getValue());
    }
}
