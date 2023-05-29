package br.com.shapeup.core.ports.output.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.user.User;

public interface UpdateTrainingDayOutputPort {
    void execute(TrainingDayEntity trainingDayEntity, User user, Training training);
}
