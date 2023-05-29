package br.com.shapeup.adapters.output.repository.mapper.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.domain.quest.training.Training;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface TrainingMapper {
    Training toDomain(TrainingEntity trainingEntity);
    List<Training> toDomain(List<TrainingEntity> trainingEntityList);
    TrainingEntity toEntity(Training trainingById, List<UserEntity> userEntities);
    List<TrainingEntity> toEntity(List<Training> trainingList);
    TrainingEntity toEntity(Training training);
}
