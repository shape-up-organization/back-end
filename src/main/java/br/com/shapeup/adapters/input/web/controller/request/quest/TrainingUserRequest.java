package br.com.shapeup.adapters.input.web.controller.request.quest;

public record TrainingUserRequest(
        String dayOfWeek,
        String period,
        String trainingId
) {
}
