package br.com.shapeup.core.ports.output.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.core.domain.quest.dto.TrainingDayEntityDto;
import java.util.List;

public interface UpdateStatusTrainingDayOutputPort {
    TrainingDayEntityDto execute(TrainingDayEntity trainingDayEntity, String status);
    void execute(List<TrainingDayEntity> trainingDayEntityList, String status);
}
