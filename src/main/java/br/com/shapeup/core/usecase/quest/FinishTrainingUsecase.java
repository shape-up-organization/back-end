package br.com.shapeup.core.usecase.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.core.domain.quest.dto.TrainingUserWithStatus;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.quest.FinishTrainingInputPort;
import br.com.shapeup.core.ports.output.quest.FindTrainingDayOutputPort;
import br.com.shapeup.core.ports.output.quest.FindTrainingOutputPort;
import br.com.shapeup.core.ports.output.quest.UpdateStatusTrainingDayOutputPort;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import br.com.shapeup.core.ports.output.xp.XpOutputPort;
import java.util.UUID;

public class FinishTrainingUsecase implements FinishTrainingInputPort {

    private final FindTrainingOutputPort findTrainingOutputPort;
    private final FindUserOutput findUserOutput;
    private final UpdateStatusTrainingDayOutputPort updateStatusTrainingDayOutputPort;
    private final XpOutputPort xpOutputPort;
    private final FindTrainingDayOutputPort findTrainingDayOutputPort;

    public FinishTrainingUsecase(
            FindTrainingOutputPort findTrainingOutputPort,
            FindUserOutput findUserOutput,
            UpdateStatusTrainingDayOutputPort updateStatusTrainingDayOutputPort,
            XpOutputPort xpOutputPort,
            FindTrainingDayOutputPort findTrainingDayOutputPort
    ) {
        this.findTrainingOutputPort = findTrainingOutputPort;
        this.findUserOutput = findUserOutput;
        this.updateStatusTrainingDayOutputPort = updateStatusTrainingDayOutputPort;
        this.xpOutputPort = xpOutputPort;
        this.findTrainingDayOutputPort = findTrainingDayOutputPort;
    }

    @Override
    public TrainingUserWithStatus execute(String username, String trainingId, String dayOfWeek, String period) {


        User user = findUserOutput.findByUsername(username);
        UUID userId = UUID.fromString(user.getId().getValue());
        Training training = findTrainingOutputPort.findById(trainingId);
        UUID trainingUuid = training.getId().getValue();

        TrainingDayEntity trainingDay = (TrainingDayEntity) findTrainingDayOutputPort
                .findByUserIdAndTrainingId(userId, trainingUuid);

        xpOutputPort.addXp(user.getUsername(), training.getXp());
        return updateStatusTrainingDayOutputPort.execute(trainingDay, "FINISHED");
    }
}
