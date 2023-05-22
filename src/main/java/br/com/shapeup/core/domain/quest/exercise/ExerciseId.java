package br.com.shapeup.core.domain.quest.exercise;

import br.com.shapeup.common.domain.Identifier;
import java.util.UUID;

public class ExerciseId extends Identifier<UUID> {

    protected ExerciseId(UUID value) {
        super(value);
    }

    public static ExerciseId unique() {
        return ExerciseId.from(UUID.randomUUID());
    }

    public static ExerciseId from(final UUID anId) {
        return new ExerciseId(anId);
    }

    public static ExerciseId from(final String anId) {
        return new ExerciseId(UUID.fromString(anId));
    }

    public UUID getValue() {
        return super.getValue();
    }
}
