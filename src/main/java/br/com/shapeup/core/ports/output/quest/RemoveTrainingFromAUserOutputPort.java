package br.com.shapeup.core.ports.output.quest;

import br.com.shapeup.core.domain.quest.training.Training;
import java.util.UUID;

public interface RemoveTrainingFromAUserOutputPort {
    void execute(Training training, UUID userId, String period);
}
