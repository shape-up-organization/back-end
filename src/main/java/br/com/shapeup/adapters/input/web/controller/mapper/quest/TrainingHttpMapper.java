package br.com.shapeup.adapters.input.web.controller.mapper.quest;

import br.com.shapeup.adapters.input.web.controller.response.quest.FindTrainingsOfUserResponse;
import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingResponse;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.core.domain.quest.dto.TrainingDayEntityDto;
import br.com.shapeup.core.domain.quest.training.Training;
import java.util.List;

public interface TrainingHttpMapper {
    List<TrainingResponse> toTrainingResponse(List<Training> trainings);

    List<TrainingDayEntityDto> toTrainingUserResponse(List<Training> trainings);

    List<FindTrainingsOfUserResponse> toFindTrainingsOfUserResponse(List<TrainingDayEntity> trainingsDaysUser);
}
