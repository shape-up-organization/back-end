package br.com.shapeup.core.ports.output.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.core.domain.quest.dto.TrainingUserWithStatus;
import java.util.List;
import java.util.UUID;

public interface FindTrainingDayOutputPort {
    List<TrainingUserWithStatus> findByUserId(UUID userId);
    Object findByUserIdAndTrainingId(UUID userId, UUID trainingId);

    List<TrainingDayEntity> findAllUsersTrainings();
}
