package br.com.shapeup.core.domain.quest.dto;

import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingDayResponse;

public record TrainingDayEntityDto(
        String id,
        String category,
        TrainingDayResponse trainingDay,
        String exercises,
        Long xp,
        String status
){}