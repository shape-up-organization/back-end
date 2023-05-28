package br.com.shapeup.core.usecase.quest;

import br.com.shapeup.core.ports.input.quest.RemoveTrainingFromUserInputPort;
import br.com.shapeup.core.ports.output.quest.FindTrainingOutputPort;
import br.com.shapeup.core.ports.output.quest.RemoveTrainingDayByTrainingIdAndUserIdOutputPort;
import br.com.shapeup.core.ports.output.quest.RemoveTrainingFromAUserOutputPort;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;
import java.util.UUID;

public class RemoveTrainingFromUserUsecase implements RemoveTrainingFromUserInputPort {

    private final FindTrainingOutputPort findTrainingOutputPort;
    private final FindUserOutput findUserOutput;
    private final UserPersistanceOutput userPersistanceOutput;
    private final RemoveTrainingDayByTrainingIdAndUserIdOutputPort removeTrainingDayByTrainingIdAndUserIdOutputPort;
    private final RemoveTrainingFromAUserOutputPort removeTrainingFromAUserOutputPort;

    public RemoveTrainingFromUserUsecase(
            FindTrainingOutputPort findTrainingOutputPort,
            FindUserOutput findUserOutput,
            UserPersistanceOutput userPersistanceOutput,

            RemoveTrainingDayByTrainingIdAndUserIdOutputPort removeTrainingDayByTrainingIdAndUserIdOutputPort,
            RemoveTrainingFromAUserOutputPort removeTrainingFromAUserOutputPort
    ) {
        this.findTrainingOutputPort = findTrainingOutputPort;
        this.findUserOutput = findUserOutput;
        this.userPersistanceOutput = userPersistanceOutput;
        this.removeTrainingDayByTrainingIdAndUserIdOutputPort = removeTrainingDayByTrainingIdAndUserIdOutputPort;
        this.removeTrainingFromAUserOutputPort = removeTrainingFromAUserOutputPort;
    }

    @Override
    public void execute(
            String username,
            String trainingId,
            String dayOfWeek,
            String period
    ) {
        var user = findUserOutput.findByUsername(username);
        var userId = UUID.fromString(user.getId().getValue());
        var training = findTrainingOutputPort.findById(trainingId);

        removeTrainingDayByTrainingIdAndUserIdOutputPort.execute(training.getId().getValue(), userId, period, dayOfWeek);
        removeTrainingFromAUserOutputPort.execute(training, userId, period);

        user.getTrainings().remove(training);
        userPersistanceOutput.save(user);
    }
}
