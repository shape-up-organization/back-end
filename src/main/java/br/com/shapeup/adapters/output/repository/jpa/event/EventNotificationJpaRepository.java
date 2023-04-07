package br.com.shapeup.adapters.output.repository.jpa.event;

import br.com.shapeup.adapters.output.repository.model.event.EventNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventNotificationJpaRepository extends JpaRepository<EventNotificationEntity, UUID> {
}
