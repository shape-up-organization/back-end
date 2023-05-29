package br.com.shapeup.core.usecase.quest;

import br.com.shapeup.adapters.input.web.controller.request.quest.TrainingUserRequest;
import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingDayResponse;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.core.domain.quest.dto.TrainingDayEntityDto;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.quest.UpdateTrainingOfUserInputPort;
import br.com.shapeup.core.ports.output.quest.FindTrainingDayOutputPort;
import br.com.shapeup.core.ports.output.quest.FindTrainingOutputPort;
import br.com.shapeup.core.ports.output.quest.UpdateTrainingDayOutputPort;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateTrainingOfUserUsecase implements UpdateTrainingOfUserInputPort {
    private final FindTrainingDayOutputPort findTrainingDayOutputPort;
    private final FindTrainingOutputPort findTrainingOutputPort;
    private final UpdateTrainingDayOutputPort updateTrainingDayOutputPort;
    private final FindUserOutput findUserOutput;

    public UpdateTrainingOfUserUsecase(
            FindTrainingDayOutputPort findTrainingDayOutputPort,
            FindTrainingOutputPort findTrainingOutputPort,
            UpdateTrainingDayOutputPort updateTrainingDayOutputPort,
            FindUserOutput findUserOutput) {
        this.findTrainingDayOutputPort = findTrainingDayOutputPort;
        this.findTrainingOutputPort = findTrainingOutputPort;
        this.updateTrainingDayOutputPort = updateTrainingDayOutputPort;
        this.findUserOutput = findUserOutput;
    }

    @Override
    public TrainingDayEntityDto execute(TrainingUserRequest trainingUserRequest, String userId) {
        String trainingId = trainingUserRequest.trainingId();
        UUID userUuid = UUID.fromString(userId);
        Training training = findTrainingOutputPort.findById(trainingId);
        User user = findUserOutput.findById(userUuid);
        var trainingDay = findTrainingDayOutputPort.findByUserIdAndPeriod(
                userUuid,
                trainingUserRequest.period()
        );

        String status = setTrainingStatus(trainingDay);
        trainingDay.setPeriod(trainingUserRequest.period());
        trainingDay.setDayOfWeek(trainingUserRequest.dayOfWeek());
        trainingDay.setStatus(status);

        updateTrainingDayOutputPort.execute(trainingDay, user, training);

        return new TrainingDayEntityDto(
                trainingDay.getId().toString(),
                training.getCategory().name(),
                new TrainingDayResponse(
                        trainingDay.getDayOfWeek(),
                        trainingDay.getPeriod()
                ),
                training.getExercises(),
                training.getXp(),
                status
        );
    }

    private static String setTrainingStatus(TrainingDayEntity trainingDay) {
        var currentDate = LocalDateTime.now();
        if (
                trainingDay.getStatus().equals("FINISHED") && trainingDay.getCompletedAt()
                .toLocalDate()
                .isEqual(currentDate.toLocalDate())
        ) {
            return "PENDING";
        } else if (trainingDay.getCompletedAt() != null && trainingDay.getCompletedAt().isBefore(currentDate)) {
            return "UNFINISHED";
        } else {
            return trainingDay.getStatus();
        }
    }
}
