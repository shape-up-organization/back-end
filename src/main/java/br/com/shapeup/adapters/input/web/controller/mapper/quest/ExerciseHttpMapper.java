package br.com.shapeup.adapters.input.web.controller.mapper.quest;

import br.com.shapeup.adapters.input.web.controller.response.quest.ExerciseResponse;
import br.com.shapeup.core.domain.quest.exercise.Exercise;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public interface ExerciseHttpMapper {
    List<ExerciseResponse> toExerciseResponse(List<Exercise> exercises);
}
