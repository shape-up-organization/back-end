package br.com.shapeup.core.ports.output.quest;

import br.com.shapeup.common.domain.enums.CategoryEnum;
import br.com.shapeup.core.domain.quest.training.Training;
import java.util.List;

public interface QuestOutputPort {
    List<Training> searchTrainingByName(String name);

    List<Training> searchTrainingByCategory(CategoryEnum category);

    List<Training> searchTrainingByUserId(String userId);
}
