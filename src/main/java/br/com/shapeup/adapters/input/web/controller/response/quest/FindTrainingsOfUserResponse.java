package br.com.shapeup.adapters.input.web.controller.response.quest;

import java.util.List;

public record FindTrainingsOfUserResponse(
        String dayOfWeek,
        List<TrainingPerPeriodResponse> trainings
) {
}
