package br.com.shapeup.adapters.input.web.controller.mapper.quest;

import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingResponse;
import br.com.shapeup.core.domain.quest.dto.TrainingUserWithStatus;
import br.com.shapeup.core.domain.quest.training.Training;
import java.util.List;

public interface TrainingHttpMapper {
    List<TrainingResponse> toTrainingResponse(List<Training> trainings);

    List<TrainingUserWithStatus> toTrainingUserResponse(List<Training> trainings);
//    TrainingUserResponse toTrainingUserResponse(Training training, String status, TrainingDayResponse trainingDayResponse);
}
