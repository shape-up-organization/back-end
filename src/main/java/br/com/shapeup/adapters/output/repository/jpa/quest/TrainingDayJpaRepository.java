package br.com.shapeup.adapters.output.repository.jpa.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrainingDayJpaRepository extends JpaRepository<TrainingDayEntity, UUID> {
    List<TrainingDayEntity> findAllByUserId(UUID userId);
    Optional<TrainingDayEntity> findByUserId(UUID userId);
    Optional<TrainingDayEntity> findByUserIdAndPeriod(UUID userId, String period);
    void deleteByTrainingIdAndUserIdAndPeriodAndDayOfWeekIgnoreCase(UUID trainingId, UUID userId, String period, String dayOfWeek);
    List<TrainingDayEntity> findAll();
    List<TrainingDayEntity> findAllByUserIdAndTrainingId(UUID userId, UUID trainingId);
    @Query(value = "DELETE FROM tb_training_days WHERE user_id = ?1", nativeQuery = true)
    void deleteByUserId(UUID userId);
}
