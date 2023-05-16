package br.com.shapeup.adapters.output.repository.jpa.quest;

import br.com.shapeup.adapters.output.repository.model.quest.TrainingEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingJpaRepository extends JpaRepository<TrainingEntity, UUID> {
    List<TrainingEntity> findAllByNameContainingIgnoreCase(String name);

    @Query("SELECT t FROM TrainingEntity t WHERE LOWER(t.category) = LOWER(:category)")
    List<TrainingEntity> findByCategoryIgnoreCase(String category);

    @Query("SELECT t FROM TrainingEntity t JOIN t.users u WHERE u.id = :userId")
    List<TrainingEntity> findByUserId(UUID userId);

}
