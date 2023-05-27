package br.com.shapeup.core.ports.output.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.user.User;

public interface InsertTrainingToSpecificDayOutputPort {

    TrainingDayEntity execute(
            User user,
            Training training,
            String day,
            String period
    );
}
