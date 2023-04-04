package br.com.shapeup.adapters.output.repository.jpa.squad;

import br.com.shapeup.adapters.output.repository.model.squad.SquadMembersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SquadMembersJpaRepository extends JpaRepository<SquadMembersEntity, UUID> {
}
