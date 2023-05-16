package br.com.shapeup.core.usecase.quest;

import br.com.shapeup.core.domain.quest.dto.TrainingUserWithStatus;
import br.com.shapeup.core.ports.input.quest.TrainingsUserWithFullStatusInputPort;
import br.com.shapeup.core.ports.output.quest.FindTrainingDayOutputPort;
import java.util.List;
import java.util.UUID;

public class TrainingsUserWithFullStatusUsecase implements TrainingsUserWithFullStatusInputPort {

    private final FindTrainingDayOutputPort findTrainingDayOutputPort;

    public TrainingsUserWithFullStatusUsecase(FindTrainingDayOutputPort findTrainingDayOutputPort) {
        this.findTrainingDayOutputPort = findTrainingDayOutputPort;
    }

    @Override
    public List<TrainingUserWithStatus> getTrainingsUserWithFullStatus(UUID userId) {
        return findTrainingDayOutputPort.findByUserId(userId);
    }
}
