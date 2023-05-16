package br.com.shapeup.core.domain.quest.dto;

import br.com.shapeup.adapters.input.web.controller.response.quest.ExerciseResponse;
import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingDayResponse;
import java.util.List;

public record TrainingUserWithStatus(
        String id,
        String category,
        TrainingDayResponse trainingDay,
        List<ExerciseResponse> exercises,
        String status
){}