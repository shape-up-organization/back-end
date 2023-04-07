package br.com.shapeup.adapters.output.repository.jpa.event;

import br.com.shapeup.adapters.output.repository.model.event.PublicEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublicEventJpaRepository extends JpaRepository<PublicEventEntity, UUID> {
}
