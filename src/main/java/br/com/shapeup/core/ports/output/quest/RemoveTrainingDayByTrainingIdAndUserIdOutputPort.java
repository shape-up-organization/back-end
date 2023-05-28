package br.com.shapeup.core.ports.output.quest;

import java.util.UUID;

public interface RemoveTrainingDayByTrainingIdAndUserIdOutputPort {

    void execute(UUID trainingId, UUID userId, String period, String dayOfWeek);
}
