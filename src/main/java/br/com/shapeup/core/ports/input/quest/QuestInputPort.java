package br.com.shapeup.core.ports.input.quest;

import br.com.shapeup.adapters.input.web.controller.request.quest.TrainingUserRequest;
import br.com.shapeup.common.domain.enums.CategoryEnum;
import br.com.shapeup.core.domain.quest.training.Training;
import java.util.List;

public interface QuestInputPort {

    List<Training> searchTrainingByName(String name);

    List<Training> searchTrainingByCategory(CategoryEnum category);

    List<Training> searchTrainingByUserId(String userId);

    Training addTrainingToUser(String username, TrainingUserRequest trainingUserRequest);
}
