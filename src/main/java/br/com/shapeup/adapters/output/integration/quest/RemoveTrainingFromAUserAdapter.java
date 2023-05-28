package br.com.shapeup.adapters.output.integration.quest;

import br.com.shapeup.adapters.output.integration.user.FindUserAdapter;
import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingDayJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.quest.TrainingMapper;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.quest.RemoveTrainingFromAUserOutputPort;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class RemoveTrainingFromAUserAdapter implements RemoveTrainingFromAUserOutputPort {

    private final TrainingJpaRepository trainingJpaRepository;
    private final FindUserAdapter findUserAdapter;
    private final UserMapper userMapper;
    private final TrainingMapper trainingMapper;
    private final TrainingDayJpaRepository trainingDayJpaRepository;

    @Override
    @Transactional
    public void execute(Training training, UUID userId, String period) {
        User user = findUserAdapter.findById(userId);
        UserEntity userEntity = userMapper.userToUserEntity(user);
        TrainingEntity trainingEntity = trainingMapper.toEntity(training, List.of(userEntity));

        var trainings = trainingDayJpaRepository.findAllByUserIdAndTrainingId(userId, trainingEntity.getId());

        userEntity.getTrainings().remove(trainingEntity);

        trainingJpaRepository.save(trainingEntity);
    }
}
