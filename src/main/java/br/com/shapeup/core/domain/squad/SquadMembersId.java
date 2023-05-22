package br.com.shapeup.core.domain.squad;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class SquadMembersId extends Identifier<String> {
    private final String value;

    private SquadMembersId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static SquadMembersId unique() {
        return SquadMembersId.from(UUID.randomUUID());
    }

    public static SquadMembersId from(final String anId) {
        return new SquadMembersId(anId);
    }

    public static SquadMembersId from(final UUID anId) {
        return new SquadMembersId(anId.toString().toLowerCase());
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
        SquadMembersId squadMembersId = (SquadMembersId) o;
        return getValue().equals(squadMembersId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
