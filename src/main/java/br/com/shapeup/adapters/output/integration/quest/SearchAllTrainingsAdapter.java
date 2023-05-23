package br.com.shapeup.adapters.output.integration.quest;

import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.quest.TrainingMapper;
import br.com.shapeup.common.exceptions.ShapeUpBaseException;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.ports.output.quest.SearchAllTrainingsOutputPort;
import io.vavr.control.Try;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchAllTrainingsAdapter implements SearchAllTrainingsOutputPort {

    private final TrainingJpaRepository trainingJpaRepository;
    private final TrainingMapper trainingMapper;

    @Override
    public List<Training> execute() {
        var trainings = Try.of(() -> trainingJpaRepository.findAll()).onSuccess(
                trainingList -> log.info("Training list found: {}", trainingList)
        ).onFailure(
                throwable -> {
                    log.error("Error while searching for training list: {}", throwable.getMessage());
                    throw new ShapeUpBaseException(throwable.getMessage(), throwable.getCause());
                }
        ).get();

        return trainingMapper.toDomain(trainings);
    }
}
