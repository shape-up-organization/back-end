package br.com.shapeup.core.domain.squad;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class SquadEventId extends Identifier<String> {
    private final String value;

    private SquadEventId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static SquadEventId unique() {
        return SquadEventId.from(UUID.randomUUID());
    }

    public static SquadEventId from(final String anId) {
        return new SquadEventId(anId);
    }

    public static SquadEventId from(final UUID anId) {
        return new SquadEventId(anId.toString().toLowerCase());
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
        SquadEventId squadEventId = (SquadEventId) o;
        return getValue().equals(squadEventId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

