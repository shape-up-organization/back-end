package br.com.shapeup.adapters.output.repository.jpa.squad;

import br.com.shapeup.adapters.output.repository.model.squad.SquadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SquadJpaRepository extends JpaRepository<SquadEntity, UUID> {
}
