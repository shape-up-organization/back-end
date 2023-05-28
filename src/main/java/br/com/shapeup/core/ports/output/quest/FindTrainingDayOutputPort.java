package br.com.shapeup.core.ports.output.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import java.util.List;
import java.util.UUID;

public interface FindTrainingDayOutputPort {
    List<TrainingDayEntity> findByUserId(UUID userId);
    TrainingDayEntity findByUserIdAndTrainingId(UUID userId, UUID trainingId);
    List<TrainingDayEntity> findAllUsersTrainings();

    List<TrainingDayEntity> findAllByUserIdAndTrainingId(UUID userId, UUID trainingId);
}
