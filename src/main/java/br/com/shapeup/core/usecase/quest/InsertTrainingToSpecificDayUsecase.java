package br.com.shapeup.core.usecase.quest;

import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.quest.InsertTrainingToSpecificDayInputPort;
import br.com.shapeup.core.ports.output.quest.FindTrainingOutputPort;
import br.com.shapeup.core.ports.output.quest.InsertTrainingToSpecificDayOutputPort;
import br.com.shapeup.core.ports.output.user.FindUserOutput;

public class InsertTrainingToSpecificDayUsecase implements InsertTrainingToSpecificDayInputPort {

    private final InsertTrainingToSpecificDayOutputPort insertTrainingToSpecificDayOutputPort;
    private final FindUserOutput findUserOutput;
    private final FindTrainingOutputPort findTrainingOutputPort;

    public InsertTrainingToSpecificDayUsecase(
            InsertTrainingToSpecificDayOutputPort insertTrainingToSpecificDayOutputPort,
            FindUserOutput findUserOutput,
            FindTrainingOutputPort findTrainingOutputPort
    ) {
        this.insertTrainingToSpecificDayOutputPort = insertTrainingToSpecificDayOutputPort;
        this.findUserOutput = findUserOutput;
        this.findTrainingOutputPort = findTrainingOutputPort;
    }

    @Override
    public void insertTrainingToSpecificDay(
            String username,
            String trainingId,
            String day,
            String period
    ) {

        User user = findUserOutput.findByUsername(username);
        Training training = findTrainingOutputPort.findById(trainingId);

        insertTrainingToSpecificDayOutputPort.execute(user, training, day, period);
    }
}
