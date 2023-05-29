package br.com.shapeup.core.ports.output.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import java.util.List;
import java.util.UUID;

public interface FindTrainingDayOutputPort {
    List<TrainingDayEntity> findByUserId(UUID userId);
    TrainingDayEntity findByUserIdAndPeriod(UUID userId, String period);
    List<TrainingDayEntity> findAllUsersTrainings();

    List<TrainingDayEntity> findAllByUserIdAndTrainingId(UUID userId, UUID trainingId);
}
