package br.com.shapeup.adapters.output.integration.quest;

import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingDayResponse;
import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingDayJpaRepository;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.core.domain.quest.dto.TrainingUserWithStatus;
import br.com.shapeup.core.ports.output.quest.UpdateStatusTrainingDayOutputPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UpdateStatusTrainingDayAdapter implements UpdateStatusTrainingDayOutputPort {

    private final TrainingDayJpaRepository trainingDayJpaRepository;

    @Override
    public TrainingUserWithStatus execute(TrainingDayEntity trainingDayEntity, String status) {
        trainingDayEntity.setStatus(status);
        trainingDayJpaRepository.save(trainingDayEntity);

        return new TrainingUserWithStatus(
                trainingDayEntity.getId().toString(),
                trainingDayEntity.getTraining().getCategory().name(),
                new TrainingDayResponse(trainingDayEntity.getDayOfWeek(), trainingDayEntity.getPeriod()),
                null,
                status
        );
    }

    @Override
    public void execute(List<TrainingDayEntity> trainingDayEntityList, String status) {
        for (TrainingDayEntity trainingDayEntity : trainingDayEntityList) {
            trainingDayEntity.setStatus(status);
            trainingDayJpaRepository.save(trainingDayEntity);
        }

        trainingDayJpaRepository.saveAll(trainingDayEntityList);
    }
}