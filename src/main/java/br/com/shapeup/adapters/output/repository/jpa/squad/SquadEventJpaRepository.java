package br.com.shapeup.adapters.output.repository.jpa.squad;

import br.com.shapeup.adapters.output.repository.model.squad.SquadEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SquadEventJpaRepository extends JpaRepository<SquadEventEntity, UUID> {
}
