package br.com.shapeup.adapters.output.integration.quest;

import br.com.shapeup.adapters.output.repository.jpa.quest.TrainingDayJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.quest.TrainingMapper;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.utils.DayOfWeekUtils;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.quest.InsertTrainingToSpecificDayOutputPort;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
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
    public TrainingDayEntity execute(User user, Training training, String day, String period) {
        day.toUpperCase();
        UserEntity userEntity = userMapper.userToUserEntity(user);
        TrainingEntity trainingEntity = trainingMapper.toEntity(training, List.of(userEntity));

        String currentDayName = LocalDate.now()
                .getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                .toUpperCase();
        Integer currentDayValue = DayOfWeekUtils.abbreviations().get(currentDayName);
        Integer trainingDayValue = DayOfWeekUtils.abbreviations().get(day);

        if(trainingDayValue < currentDayValue) {
            var trainingDayEntity = TrainingDayEntity.builder()
                    .training(trainingEntity)
                    .dayOfWeek(day)
                    .user(userEntity)
                    .period(period)
                    .status("UNCOMPLETED")
                    .build();

            return trainingDayJpaRepository.save(trainingDayEntity);
        }

            var trainingDayEntity = TrainingDayEntity.builder()
                    .training(trainingEntity)
                    .dayOfWeek(day)
                    .user(userEntity)
                    .period(period)
                    .status("PENDING")
                    .build();

            return trainingDayJpaRepository.save(trainingDayEntity);
    }
}
