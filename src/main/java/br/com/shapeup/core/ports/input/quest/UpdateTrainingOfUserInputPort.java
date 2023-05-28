package br.com.shapeup.core.ports.input.quest;

import br.com.shapeup.adapters.input.web.controller.request.quest.TrainingUserRequest;
import br.com.shapeup.core.domain.quest.dto.TrainingDayEntityDto;

public interface UpdateTrainingOfUserInputPort {
    TrainingDayEntityDto execute(TrainingUserRequest trainingUserRequest, String userId);
}
