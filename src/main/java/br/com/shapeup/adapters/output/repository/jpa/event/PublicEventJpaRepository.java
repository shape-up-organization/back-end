package br.com.shapeup.adapters.output.repository.jpa.event;

import br.com.shapeup.adapters.output.repository.model.event.PublicEventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicEventJpaRepository extends JpaRepository<PublicEventEntity, UUID> {
}
