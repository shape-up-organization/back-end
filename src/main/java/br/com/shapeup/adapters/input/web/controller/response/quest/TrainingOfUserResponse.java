package br.com.shapeup.adapters.input.web.controller.response.quest;

public record TrainingOfUserResponse(
        String id,
        String name,
        String category,
        String description,
        Integer duration,
        String classification,
        Long unlockXp,
        String exercises,
        String status
) {
}
