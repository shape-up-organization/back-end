package br.com.shapeup.adapters.output.repository.mapper.quest;

import br.com.shapeup.adapters.output.repository.model.quest.ExerciseEntity;
import br.com.shapeup.core.domain.quest.exercise.Exercise;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface ExerciseMapper {
    Exercise toDomain(ExerciseEntity exerciseEntity);

    List<Exercise> toDomain(List<ExerciseEntity> exerciseEntity);

    List<ExerciseEntity> toEntity(List<Exercise> exercises);
}
