package br.com.shapeup.core.ports.input.quest;

public interface InsertTrainingToSpecificDayInputPort {

    void insertTrainingToSpecificDay(
            String username,
            String trainingId,
            String day,
            String period
    );
}
