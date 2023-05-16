package br.com.shapeup.adapters.output.integration.quest;

import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingDayJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.quest.TrainingMapper;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.quest.InsertTrainingToSpecificDayOutputPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InsertTrainingToSpecificDayAdapter implements InsertTrainingToSpecificDayOutputPort {

    private final TrainingDayJpaRepository trainingDayJpaRepository;
    private final TrainingMapper trainingMapper;
    private final UserMapper userMapper;

    @Override
    public void execute(User user, Training training, String day, String period) {

        UserEntity userEntity = userMapper.userToUserEntity(user);
        TrainingEntity trainingEntity = trainingMapper.toEntity(training, List.of(userEntity));

        var trainingDayEntity = TrainingDayEntity.builder()
                .training(trainingEntity)
                .dayOfWeek(day)
                .user(userEntity)
                .period(period)
                .status("PENDING")
                .build();

        trainingDayJpaRepository.save(trainingDayEntity);
    }
}
