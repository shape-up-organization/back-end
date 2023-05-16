package br.com.shapeup.adapters.output.repository.jpa.squad;

import br.com.shapeup.adapters.output.repository.model.squad.SquadMembersEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SquadMembersJpaRepository extends JpaRepository<SquadMembersEntity, UUID> {
}
