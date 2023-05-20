package br.com.shapeup.adapters.output.integration.quest;

import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingDayJpaRepository;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.common.exceptions.ShapeUpNotFoundException;
import br.com.shapeup.core.ports.output.quest.FindTrainingDayOutputPort;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class FindTrainingDayAdapter implements FindTrainingDayOutputPort {

    private final TrainingDayJpaRepository trainingDayJpaRepository;

    @Override
    public List<TrainingDayEntity> findByUserId(UUID userId) {
        List<TrainingDayEntity> trainingDayEntityDtoList = trainingDayJpaRepository.findAllByUserId(userId);

        return trainingDayEntityDtoList;
    }

    @Override
    public Object findByUserIdAndTrainingId(UUID userId, UUID trainingId) {
        return trainingDayJpaRepository.findByUserIdAndTrainingId(userId, trainingId).orElseThrow(() -> new ShapeUpNotFoundException("Training not found"));
    }

    @Override
    public List<TrainingDayEntity> findAllUsersTrainings() {
        return trainingDayJpaRepository.findAll();
    }
}
