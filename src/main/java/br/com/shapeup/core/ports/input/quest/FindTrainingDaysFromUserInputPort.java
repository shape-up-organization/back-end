package br.com.shapeup.core.ports.input.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import java.util.List;
import java.util.UUID;

public interface FindTrainingDaysFromUserInputPort {
    List<TrainingDayEntity> execute(UUID userId);
}
