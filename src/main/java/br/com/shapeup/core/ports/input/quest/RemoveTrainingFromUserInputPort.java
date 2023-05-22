package br.com.shapeup.core.ports.input.quest;

public interface RemoveTrainingFromUserInputPort {

    void execute(
            String username,
            String trainingId,
            String dayOfWeek,
            String period
    );
}
