package br.com.shapeup.core.usecase.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.core.ports.input.quest.FindTrainingDaysFromUserInputPort;
import br.com.shapeup.core.ports.output.quest.FindTrainingDayOutputPort;
import java.util.List;
import java.util.UUID;

public class FindTrainingDaysFromUserUsecase implements FindTrainingDaysFromUserInputPort {

    private final FindTrainingDayOutputPort findTrainingDayOutputPort;

    public FindTrainingDaysFromUserUsecase(
            FindTrainingDayOutputPort findTrainingDayOutputPort
    ) {
        this.findTrainingDayOutputPort = findTrainingDayOutputPort;
    }

    @Override
    public List<TrainingDayEntity> execute(UUID userId) {

        List<TrainingDayEntity> trainingDayEntities = findTrainingDayOutputPort.findByUserId(userId);

        return trainingDayEntities;
    }
}
