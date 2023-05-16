package br.com.shapeup.adapters.input.web.controller.mapper.quest;

import br.com.shapeup.adapters.input.web.controller.response.quest.ExerciseResponse;
import br.com.shapeup.core.domain.quest.exercise.Exercise;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ExerciseHttpImpl implements ExerciseHttpMapper{
    @Override
    public List<ExerciseResponse> toExerciseResponse(List<Exercise> exercises) {
        List<ExerciseResponse> exerciseResponses = new ArrayList<>();

        for (Exercise exercise : exercises) {
            exerciseResponses.add(new ExerciseResponse(
                    exercise.getId().getValue().toString(),
                    exercise.getExercise(),
                    exercise.getDuration()
            ));
        }

        return exerciseResponses;
    }
}
