package br.com.shapeup.core.ports.input.quest;

import br.com.shapeup.core.domain.quest.dto.TrainingDayEntityDto;

public interface FinishTrainingInputPort {
    TrainingDayEntityDto execute(String username, String trainingId, String dayOfWeek, String period);
}
