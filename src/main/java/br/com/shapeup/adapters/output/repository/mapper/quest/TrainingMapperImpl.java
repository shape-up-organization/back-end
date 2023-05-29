package br.com.shapeup.adapters.output.repository.mapper.quest;

import br.com.shapeup.adapters.output.repository.model.quest.ExerciseEntity;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.quest.training.TrainingId;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class TrainingMapperImpl implements TrainingMapper {

    @Override
    public Training toDomain(TrainingEntity trainingEntity) {
        return Training.create(
                TrainingId.from(trainingEntity.getId()),
                trainingEntity.getName(),
                trainingEntity.getCategory(),
                trainingEntity.getDuration(),
                trainingEntity.getDescription(),
                trainingEntity.getXp(),
                trainingEntity.getClassification(),
                trainingEntity.getUnlockXp(),
                trainingEntity.getExercises()
                        .stream()
                        .map(ExerciseEntity::getExercise)
                        .toList()
        );
    }
    @Override
    public List<Training> toDomain(List<TrainingEntity> trainingEntityList) {
        List<Training> trainingList = new ArrayList<>();

        for (TrainingEntity trainingEntity : trainingEntityList) {
            Training training = toDomain(trainingEntity);
            trainingList.add(training);
        }

        return trainingList;
    }

    @Override
    public TrainingEntity toEntity(Training training, List<UserEntity> userEntities) {
        return TrainingEntity.builder()
                .id(training.getId().getValue())
                .name(training.getName())
                .category(training.getCategory())
                .duration(training.getDuration())
                .description(training.getDescription())
                .unlockXp(training.getUnlockXp())
                .xp(training.getXp())
                .classification(training.getClassification())
                .users(userEntities)
                .build();
    }

    @Override
    public List<TrainingEntity> toEntity(List<Training> trainingList) {
        List<TrainingEntity> trainingEntityList = new ArrayList<>();

        for (Training training : trainingList) {
            TrainingEntity trainingEntity = toEntity(training, null);
            trainingEntityList.add(trainingEntity);
        }

        return trainingEntityList;
    }

    @Override
    public TrainingEntity toEntity(Training training) {
        return TrainingEntity.builder()
                .id(training.getId().getValue())
                .name(training.getName())
                .category(training.getCategory())
                .duration(training.getDuration())
                .description(training.getDescription())
                .unlockXp(training.getUnlockXp())
                .xp(training.getXp())
                .classification(training.getClassification())
                .build();
    }
}
