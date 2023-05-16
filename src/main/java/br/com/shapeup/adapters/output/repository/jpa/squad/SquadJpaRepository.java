package br.com.shapeup.adapters.output.repository.jpa.squad;

import br.com.shapeup.adapters.output.repository.model.squad.SquadEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SquadJpaRepository extends JpaRepository<SquadEntity, UUID> {
}
