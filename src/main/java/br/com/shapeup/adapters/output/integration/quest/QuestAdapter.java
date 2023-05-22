package br.com.shapeup.adapters.output.integration.quest;

import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingDayJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.quest.TrainingMapper;
import br.com.shapeup.common.domain.enums.CategoryEnum;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.ports.output.quest.QuestOutputPort;
import io.vavr.control.Try;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class QuestAdapter implements QuestOutputPort {

    private final TrainingJpaRepository trainingJpaRepository;
    private final TrainingMapper trainingMapper;
    private final TrainingDayJpaRepository trainingDayJpaRepository;

    @Override
    public List<Training> searchTrainingByName(String name) {
        return Try.of(() -> {
                    var trainingsEntity = trainingJpaRepository.findAllByNameContainingIgnoreCase(name);
                    return trainingMapper.toDomain(trainingsEntity);
                })
                .onFailure(throwable -> log.error("Error on search training by name: {}", throwable.getMessage()))
                .getOrElseThrow(() -> new RuntimeException("Error on search training by name"));
    }

    @Override
    public List<Training> searchTrainingByCategory(CategoryEnum category) {
        return Try.of(() -> {
                    var trainingsEntity = trainingJpaRepository.findByCategoryIgnoreCase(category.name());
                    return trainingMapper.toDomain(trainingsEntity);
                })
                .onFailure(throwable -> log.error("Error on search training by category: {}", throwable.getMessage()))
                .getOrElseThrow(() -> new RuntimeException("Error on search training by category"));
    }

    @Override
    public List<Training> searchTrainingByUserId(String userId) {
        return Try.of(() -> {
                    var trainingsEntity = trainingJpaRepository.findByUserId(UUID.fromString(userId));

                    return trainingMapper.toDomain(trainingsEntity);
                })
                .onFailure(throwable -> log.error("Error when trying search training of user cause: {}", throwable.getMessage()))
                .getOrElseThrow(() -> new RuntimeException("Error on search training by user id"));
    }
}
