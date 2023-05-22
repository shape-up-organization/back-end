package br.com.shapeup.adapters.output.integration.quest;

import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.quest.TrainingMapper;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingEntity;
import br.com.shapeup.common.exceptions.ShapeUpNotFoundException;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.ports.output.quest.FindTrainingOutputPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindTrainingAdapter implements FindTrainingOutputPort {

    private final TrainingJpaRepository trainingJpaRepository;
    private final TrainingMapper trainingMapper;

    @Override
    public Training findById(String id) {
        TrainingEntity trainingEntity = trainingJpaRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new ShapeUpNotFoundException("Training not found"));

        return trainingMapper.toDomain(trainingEntity);
    }
}
