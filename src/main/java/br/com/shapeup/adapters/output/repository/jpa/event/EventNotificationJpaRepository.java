package br.com.shapeup.adapters.output.repository.jpa.event;

import br.com.shapeup.adapters.output.repository.model.event.EventNotificationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventNotificationJpaRepository extends JpaRepository<EventNotificationEntity, UUID> {
}
