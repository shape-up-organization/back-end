package br.com.shapeup.adapters.input.web.controller.response.quest;

public record TrainingResponse(
        String id,
        String name,
        String category,
        String description,
        Integer duration,
        String classification,
        Long xp,
        Long unlockXp,
        String exercises
){}
