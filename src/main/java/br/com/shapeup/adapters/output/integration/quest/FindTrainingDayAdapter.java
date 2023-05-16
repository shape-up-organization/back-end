package br.com.shapeup.adapters.output.integration.quest;

import br.com.shapeup.adapters.input.web.controller.mapper.quest.ExerciseHttpMapper;
import br.com.shapeup.adapters.input.web.controller.response.quest.ExerciseResponse;
import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingDayResponse;
import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingDayJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.quest.ExerciseMapper;
import br.com.shapeup.adapters.output.repository.model.quest.ExerciseEntity;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.common.exceptions.ShapeUpNotFoundException;
import br.com.shapeup.core.domain.quest.dto.TrainingUserWithStatus;
import br.com.shapeup.core.domain.quest.exercise.Exercise;
import br.com.shapeup.core.ports.output.quest.FindTrainingDayOutputPort;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class FindTrainingDayAdapter implements FindTrainingDayOutputPort {

    private final TrainingDayJpaRepository trainingDayJpaRepository;
    private final ExerciseMapper exerciseMapper;
    private final ExerciseHttpMapper exerciseHttpMapper;

    @Override
    public List<TrainingUserWithStatus> findByUserId(UUID userId) {
        List<TrainingDayEntity> trainingDayEntityList = trainingDayJpaRepository.findAllByUserId(userId);
        List<TrainingUserWithStatus> trainingUserResponseWithStatus = new ArrayList<>();

        for (TrainingDayEntity trainingDay : trainingDayEntityList) {
            TrainingUserWithStatus trainingUserResponse = createTrainingUserResponse(trainingDay);
            trainingUserResponseWithStatus.add(trainingUserResponse);
        }

        return trainingUserResponseWithStatus;
    }

    @Override
    public Object findByUserIdAndTrainingId(UUID userId, UUID trainingId) {
        return trainingDayJpaRepository.findByUserIdAndTrainingId(userId, trainingId).orElseThrow(() -> new ShapeUpNotFoundException("Training not found"));
    }

    @Override
    public List<TrainingDayEntity> findAllUsersTrainings() {
        return trainingDayJpaRepository.findAll();
    }

    private TrainingUserWithStatus createTrainingUserResponse(TrainingDayEntity trainingDay) {
        String id = trainingDay.getId().toString();
        String category = trainingDay.getTraining().getCategory().name();
        TrainingDayResponse trainingDayResponse = new TrainingDayResponse(trainingDay.getDayOfWeek(), trainingDay.getPeriod());
        List<ExerciseResponse> exerciseResponses = mapExercises(trainingDay.getTraining().getExercises());
        String status = trainingDay.getStatus();

        return new TrainingUserWithStatus(id, category, trainingDayResponse, exerciseResponses, status);
    }

    private List<ExerciseResponse> mapExercises(List<ExerciseEntity> exerciseEntities) {
        List<Exercise> exercises = exerciseEntities.stream()
                .map(exerciseMapper::toDomain)
                .collect(Collectors.toList());

        return exerciseHttpMapper.toExerciseResponse(exercises);
    }
}
