package br.com.shapeup.core.ports.input.quest;

import br.com.shapeup.core.domain.quest.dto.TrainingUserWithStatus;
import java.util.List;
import java.util.UUID;

public interface TrainingsUserWithFullStatusInputPort {
    List<TrainingUserWithStatus> getTrainingsUserWithFullStatus(UUID userId);
}
