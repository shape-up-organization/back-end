package br.com.shapeup.core.ports.output.quest;

import br.com.shapeup.core.domain.quest.dto.TrainingDayEntityDto;
import java.util.List;

public interface UpdateStatusTrainingDayOutputPort {
    TrainingDayEntityDto execute(br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity trainingDayEntity, String status);
    void execute(List<br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity> trainingDayEntityList, String status);
}
