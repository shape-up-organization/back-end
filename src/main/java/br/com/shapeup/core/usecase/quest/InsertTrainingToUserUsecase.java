package br.com.shapeup.core.usecase.quest;

import br.com.shapeup.common.exceptions.ShapeUpNotFoundException;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.quest.InsertTrainingToUserInputPort;
import br.com.shapeup.core.ports.output.quest.FindTrainingOutputPort;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;

public class InsertTrainingToUserUsecase implements InsertTrainingToUserInputPort {

    private final FindTrainingOutputPort findTrainingOutputPort;
    private final FindUserOutput findUserOutput;
    private final UserPersistanceOutput userPersistanceOutput;

    public InsertTrainingToUserUsecase(
            FindTrainingOutputPort findTrainingOutputPort,
            FindUserOutput findUserOutput,
            UserPersistanceOutput userPersistanceOutput
    ) {
        this.findTrainingOutputPort = findTrainingOutputPort;
        this.findUserOutput = findUserOutput;
        this.userPersistanceOutput = userPersistanceOutput;
    }


    @Override
    public void insertTrainingToUser(String username, String trainingId) {
        User user = findUserOutput.findByUsername(username);

        Training training = findTrainingOutputPort.findById(trainingId);

        if (training == null) {
            throw new ShapeUpNotFoundException(String.format("Training with id %s not found", trainingId));
        }

        user.getTrainings().add(training);

        userPersistanceOutput.save(user);
    }
}
