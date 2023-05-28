package br.com.shapeup.adapters.output.integration.quest;

import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingDayJpaRepository;
import br.com.shapeup.core.ports.output.quest.RemoveTrainingDayByTrainingIdAndUserIdOutputPort;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class RemoveTrainingDayByTrainingIdAndUserIdAdapter implements RemoveTrainingDayByTrainingIdAndUserIdOutputPort {

    private final TrainingDayJpaRepository trainingDayJpaRepository;

    @Override
    @Transactional
    public void execute(UUID trainingId, UUID userId, String period, String dayOfWeek) {
        trainingDayJpaRepository.deleteByTrainingIdAndUserIdAndPeriodAndDayOfWeekIgnoreCase(trainingId, userId, period, dayOfWeek);
    }
}
