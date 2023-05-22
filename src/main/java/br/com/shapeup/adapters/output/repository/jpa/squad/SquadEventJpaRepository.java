package br.com.shapeup.adapters.output.repository.jpa.squad;

import br.com.shapeup.adapters.output.repository.model.squad.SquadEventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SquadEventJpaRepository extends JpaRepository<SquadEventEntity, UUID> {
}
