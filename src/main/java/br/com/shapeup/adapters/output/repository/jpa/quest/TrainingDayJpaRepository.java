package br.com.shapeup.adapters.output.repository.jpa.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingDayJpaRepository extends JpaRepository<TrainingDayEntity, UUID> {
    List<TrainingDayEntity> findAllByUserId(UUID userId);
    Optional<TrainingDayEntity> findByUserId(UUID userId);
    Optional<TrainingDayEntity> findByUserIdAndTrainingId(UUID userId, UUID trainingId);
    void deleteByTrainingIdAndUserId(UUID trainingId, UUID userId);
    List<TrainingDayEntity> findAll();
}