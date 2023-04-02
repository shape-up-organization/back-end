package br.com.shapeup.core.domain.level;

import br.com.shapeup.common.domain.Identifier;
import br.com.shapeup.core.domain.squad.SquadId;

import java.util.Objects;
import java.util.UUID;

public class LevelId extends Identifier {
    private final String value;

    private LevelId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static LevelId unique() {
        return LevelId.from(UUID.randomUUID());
    }

    public static LevelId from(final String anId) {
        return new LevelId(anId);
    }

    public static LevelId from(final UUID anId) {
        return new LevelId(anId.toString().toLowerCase());
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
        LevelId levelId= (LevelId) o;
        return getValue().equals(levelId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
