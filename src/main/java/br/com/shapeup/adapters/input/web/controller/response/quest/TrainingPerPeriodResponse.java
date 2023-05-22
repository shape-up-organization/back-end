package br.com.shapeup.adapters.input.web.controller.response.quest;

public record TrainingPerPeriodResponse(
    String period,
    TrainingOfUserResponse training
) {
}
