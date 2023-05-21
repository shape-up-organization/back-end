package br.com.shapeup.adapters.input.web.controller.response.quest;

import java.util.List;

public record TrainingOfUserResponse(
        String id,
        String name,
        String category,
        String description,
        Integer duration,
        String classification,
        Long xp,
        Long unlockXp,
        List<String> exercises,
        String status
) {
}
