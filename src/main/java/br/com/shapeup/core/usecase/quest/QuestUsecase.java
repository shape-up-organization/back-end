package br.com.shapeup.core.usecase.quest;

import br.com.shapeup.adapters.input.web.controller.request.quest.TrainingUserRequest;
import br.com.shapeup.common.domain.enums.CategoryEnum;
import br.com.shapeup.common.exceptions.quest.InsufficientXPException;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.quest.QuestInputPort;
import br.com.shapeup.core.ports.output.quest.FindTrainingOutputPort;
import br.com.shapeup.core.ports.output.quest.InsertTrainingToSpecificDayOutputPort;
import br.com.shapeup.core.ports.output.quest.QuestOutputPort;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;
import java.util.List;

public class QuestUsecase implements QuestInputPort {

    private final QuestOutputPort questOutputPort;
    private final FindUserOutput findUserOutput;
    private final InsertTrainingToSpecificDayOutputPort insertTrainingToSpecificDayOutputPort;
    private final FindTrainingOutputPort findTrainingOutputPort;
    private final UserPersistanceOutput userPersistanceOutput;

    public QuestUsecase(
            QuestOutputPort questOutputPort,
            FindUserOutput findUserOutput,
            InsertTrainingToSpecificDayOutputPort insertTrainingToSpecificDayOutputPort,
            FindTrainingOutputPort findTrainingOutputPort,
            UserPersistanceOutput userPersistanceOutput
    ) {

        this.questOutputPort = questOutputPort;
        this.findUserOutput = findUserOutput;
        this.insertTrainingToSpecificDayOutputPort = insertTrainingToSpecificDayOutputPort;
        this.findTrainingOutputPort = findTrainingOutputPort;
        this.userPersistanceOutput = userPersistanceOutput;
    }

    @Override
    public List<Training> searchTrainingByName(String name) {
        return questOutputPort.searchTrainingByName(name);
    }

    @Override
    public List<Training> searchTrainingByCategory(CategoryEnum category) {
        return questOutputPort.searchTrainingByCategory(category);
    }

    @Override
    public List<Training> searchTrainingByUserId(String userId) {
        return questOutputPort.searchTrainingByUserId(userId);
    }

    @Override
    public Training addTrainingToUser(String username, TrainingUserRequest trainingUserRequest) {
        User user = findUserOutput.findByUsername(username);
        Training training = findTrainingOutputPort.findById(trainingUserRequest.trainingId());

        var userXp = user.getXp();
        var trainingUnlockXp = training.getUnlockXp();

        if(userXp < trainingUnlockXp) {
            throw new InsufficientXPException("User does not have enough xp to unlock this training");
        }

        user.getTrainings().add(training);
        userPersistanceOutput.save(user);

        insertTrainingToSpecificDayOutputPort.execute(
                user,
                training,
                trainingUserRequest.dayOfWeek().toUpperCase(),
                trainingUserRequest.period()
        );

        return training;
    }
}
