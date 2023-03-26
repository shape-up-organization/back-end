package br.com.shapeup.adapters.output.repository.model.event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_event_notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventNotificationEntity {
    @Id
    private UUID id = UUID.randomUUID();

    @Column
    private String title;

    @Column
    private String message;

    @Column(name = "fk_public_event_id", insertable = false, updatable = false)
    private UUID fkPublicEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_public_event_id", referencedColumnName = "id")
    private PublicEventEntity publicEventEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventNotificationEntity that = (EventNotificationEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(message, that.message) && Objects.equals(fkPublicEventId, that.fkPublicEventId) && Objects.equals(publicEventEntity, that.publicEventEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, message, fkPublicEventId, publicEventEntity);
    }
}
