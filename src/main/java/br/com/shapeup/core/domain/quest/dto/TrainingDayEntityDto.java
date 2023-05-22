package br.com.shapeup.core.domain.quest.dto;

import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingDayResponse;
import java.util.List;

public record TrainingDayEntityDto(
        String id,
        String category,
        TrainingDayResponse trainingDay,
        List<String> exercises,
        Long xp,
        String status
){}