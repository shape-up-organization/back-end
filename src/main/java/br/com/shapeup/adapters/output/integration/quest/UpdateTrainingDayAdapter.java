package br.com.shapeup.adapters.output.integration.quest;

import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingDayJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.common.exceptions.ShapeUpBaseException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.quest.UpdateTrainingDayOutputPort;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateTrainingDayAdapter implements UpdateTrainingDayOutputPort {

    private final TrainingDayJpaRepository trainingDayJpaRepository;
    private final UserMapper userMapper;

    @Override
    public void execute(TrainingDayEntity trainingDayEntity, User user) {
        var userEntity = userMapper.userToUserEntity(user);
        trainingDayEntity.setUser(userEntity);
        Try.run(() -> trainingDayJpaRepository.save(trainingDayEntity))
                .onFailure(e -> {
                    log.error("Error on save training day: {}", e.getMessage());
                    throw new ShapeUpBaseException("Error on save training day", e.getCause());
                });
    }
}
