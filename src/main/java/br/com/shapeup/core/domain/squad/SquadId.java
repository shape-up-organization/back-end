package br.com.shapeup.core.domain.squad;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class SquadId  extends Identifier<String> {
    private final String value;

    private SquadId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static SquadId unique() {
        return SquadId.from(UUID.randomUUID());
    }

    public static SquadId from(final String anId) {
        return new SquadId(anId);
    }

    public static SquadId from(final UUID anId) {
        return new SquadId(anId.toString().toLowerCase());
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
        SquadId SquadId = (SquadId) o;
        return getValue().equals(SquadId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}