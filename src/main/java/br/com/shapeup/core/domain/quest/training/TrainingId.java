package br.com.shapeup.core.domain.quest.training;

import br.com.shapeup.common.domain.Identifier;
import java.util.UUID;

public class TrainingId extends Identifier<UUID> {

    protected TrainingId(UUID value) {
        super(value);
    }

    public static TrainingId unique() {
        return TrainingId.from(UUID.randomUUID());
    }

    public static TrainingId from(final UUID anId) {
        return new TrainingId(anId);
    }

    public static TrainingId from(final String anId) {
        return new TrainingId(UUID.fromString(anId));
    }

    public UUID getValue() {
        return super.getValue();
    }
}
