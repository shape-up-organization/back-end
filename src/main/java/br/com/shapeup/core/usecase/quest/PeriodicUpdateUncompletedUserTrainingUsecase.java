package br.com.shapeup.core.usecase.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.common.exceptions.ShapeUpBaseException;
import br.com.shapeup.core.ports.input.quest.PeriodicUpdateUncompletedUserTrainingInputPort;
import br.com.shapeup.core.ports.output.quest.FindTrainingDayOutputPort;
import br.com.shapeup.core.ports.output.quest.UpdateStatusTrainingDayOutputPort;
import io.vavr.control.Try;
import java.util.List;

public class PeriodicUpdateUncompletedUserTrainingUsecase implements PeriodicUpdateUncompletedUserTrainingInputPort {

    private final FindTrainingDayOutputPort findTrainingDayOutputPort;
    private final UpdateStatusTrainingDayOutputPort updateStatusTrainingDayOutputPort;

    public PeriodicUpdateUncompletedUserTrainingUsecase(
            FindTrainingDayOutputPort findTrainingDayOutputPort,
            UpdateStatusTrainingDayOutputPort updateStatusTrainingDayOutputPort
    ) {
        this.findTrainingDayOutputPort = findTrainingDayOutputPort;
        this.updateStatusTrainingDayOutputPort = updateStatusTrainingDayOutputPort;
    }

    @Override
    public void execute() {
        List<TrainingDayEntity> allUsersTrainings = findTrainingDayOutputPort.findAllUsersTrainings();
        var usersWithPendingStatus = allUsersTrainings.stream()
                .filter(trainingDay -> trainingDay.getStatus().equals("PENDING"))
                .toList();

        Try.run(() -> {
            updateStatusTrainingDayOutputPort.execute(usersWithPendingStatus, "UNCOMPLETED");
        }).onFailure(failure -> {
            throw new ShapeUpBaseException(failure.getMessage(), failure.getCause());
        });
    }
}
