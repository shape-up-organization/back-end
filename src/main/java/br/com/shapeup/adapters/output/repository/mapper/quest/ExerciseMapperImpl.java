package br.com.shapeup.adapters.output.repository.mapper.quest;

import br.com.shapeup.adapters.output.repository.model.quest.ExerciseEntity;
import br.com.shapeup.core.domain.quest.exercise.Exercise;
import br.com.shapeup.core.domain.quest.exercise.ExerciseId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ExerciseMapperImpl implements ExerciseMapper{
    @Override
    public Exercise toDomain(ExerciseEntity exerciseEntity) {
        return Exercise.create(
                ExerciseId.from(exerciseEntity.getId()),
                exerciseEntity.getExercise()
        );
    }

    @Override
    public List<Exercise> toDomain(List<ExerciseEntity> exerciseEntityList) {
        List<Exercise> exerciseList = new ArrayList<>();

        for (ExerciseEntity exerciseEntity : exerciseEntityList) {
            Exercise exercise = toDomain(exerciseEntity);
            exerciseList.add(exercise);
        }

        return exerciseList;
    }

    public ExerciseEntity toEntity(Exercise exerciseById) {
        return ExerciseEntity.builder()
                             .id(exerciseById.getId().getValue())
                             .exercise(exerciseById.getExercise())
                             .build();
    }

    @Override
    public List<ExerciseEntity> toEntity(List<Exercise> exercises) {
        List<ExerciseEntity> exerciseEntityList = new ArrayList<>();

        for (Exercise exercise : exercises) {
            ExerciseEntity exerciseEntity = toEntity(exercise);
            exerciseEntityList.add(exerciseEntity);
        }

        return exerciseEntityList;
    }
}
