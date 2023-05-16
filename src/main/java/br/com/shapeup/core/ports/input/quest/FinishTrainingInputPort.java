package br.com.shapeup.core.ports.input.quest;

import br.com.shapeup.core.domain.quest.dto.TrainingUserWithStatus;

public interface FinishTrainingInputPort {
    TrainingUserWithStatus execute(String username, String trainingId, String dayOfWeek, String period);
}
