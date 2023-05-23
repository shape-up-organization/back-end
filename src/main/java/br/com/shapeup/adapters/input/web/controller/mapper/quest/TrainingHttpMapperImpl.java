package br.com.shapeup.adapters.input.web.controller.mapper.quest;

import br.com.shapeup.adapters.input.web.controller.response.quest.FindTrainingsOfUserResponse;
import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingOfUserResponse;
import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingPerPeriodResponse;
import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingResponse;
import br.com.shapeup.adapters.output.repository.model.quest.ExerciseEntity;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingEntity;
import br.com.shapeup.core.domain.quest.dto.TrainingDayEntityDto;
import br.com.shapeup.core.domain.quest.training.Training;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Primary
public class TrainingHttpMapperImpl implements TrainingHttpMapper {

    @Override
    public List<TrainingResponse> toTrainingResponse(List<Training> trainings) {
        List<TrainingResponse> trainingResponses = new ArrayList<>();

        for (Training training : trainings) {
            TrainingResponse trainingResponse = new TrainingResponse(
                    training.getId().getValue().toString(),
                    training.getName(),
                    training.getCategory().name(),
                    training.getDescription(),
                    training.getDuration(),
                    training.getClassification(),
                    training.getXp(),
                    training.getUnlockXp(),
                    training.getExercises()
            );

            trainingResponses.add(trainingResponse);
        }

        return trainingResponses;
    }

    @Override
    public List<TrainingDayEntityDto> toTrainingUserResponse(List<Training> trainings) {
        return null;
    }

    @Override
    public List<FindTrainingsOfUserResponse> toFindTrainingsOfUserResponse(List<TrainingDayEntity> trainingsDaysUser) {
        List<FindTrainingsOfUserResponse> findTrainingsOfUserResponses = new ArrayList<>();

        Map<String, List<TrainingPerPeriodResponse>> trainingsPerDayOfWeek = new HashMap<>();

        for (TrainingDayEntity trainingDay : trainingsDaysUser) {
            String dayOfWeek = trainingDay.getDayOfWeek();
            TrainingPerPeriodResponse trainingPerPeriodResponse = createTrainingPerPeriodResponse(trainingDay);

            trainingsPerDayOfWeek.computeIfAbsent(dayOfWeek, k -> new ArrayList<>()).add(trainingPerPeriodResponse);
        }

        for (Map.Entry<String, List<TrainingPerPeriodResponse>> entry : trainingsPerDayOfWeek.entrySet()) {
            String dayOfWeek = entry.getKey();
            List<TrainingPerPeriodResponse> trainingsForDay = entry.getValue();
            FindTrainingsOfUserResponse findTrainingsOfUserResponse = new FindTrainingsOfUserResponse(dayOfWeek, trainingsForDay);
            findTrainingsOfUserResponses.add(findTrainingsOfUserResponse);
        }

        return findTrainingsOfUserResponses;
    }

    private TrainingPerPeriodResponse createTrainingPerPeriodResponse(TrainingDayEntity trainingDay) {
        TrainingEntity training = trainingDay.getTraining();
        TrainingOfUserResponse trainingOfUserResponse = new TrainingOfUserResponse(
                training.getId().toString(),
                training.getName(),
                training.getCategory().name(),
                training.getDescription(),
                training.getDuration(),
                training.getClassification(),
                training.getXp(),
                training.getUnlockXp(),
                training.getExercises()
                        .stream()
                        .map(ExerciseEntity::getExercise)
                        .toList(),
                trainingDay.getStatus()
        );

        return new TrainingPerPeriodResponse(trainingDay.getPeriod(), trainingOfUserResponse);
    }
}
