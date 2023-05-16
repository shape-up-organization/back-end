package br.com.shapeup.adapters.input.web.controller.mapper.quest;

import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingResponse;
import br.com.shapeup.core.domain.quest.dto.TrainingUserWithStatus;
import br.com.shapeup.core.domain.quest.training.Training;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Primary
public class TrainingHttpMapperImpl implements TrainingHttpMapper {
    private final ExerciseHttpMapper exerciseHttpMapper;

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
                    training.getClassification().name(),
                    exerciseHttpMapper.toExerciseResponse(training.getExercises())
            );

            trainingResponses.add(trainingResponse);
        }

        return trainingResponses;
    }

    @Override
    public List<TrainingUserWithStatus> toTrainingUserResponse(List<Training> trainings) {
        return null;
    }

//    @Override
//    public TrainingUserResponse toTrainingUserResponse(
//            Training training,
//            String status,
//            TrainingDayResponse trainingDayResponse
//    ) {
//        return new TrainingUserResponse(
//                training.getId().getValue().toString(),
//                training.getCategory().name(),
//                status,
//                trainingDayResponse,
//                exerciseHttpMapper.toExerciseResponse(training.getExercises())
//        );
//    }
}
