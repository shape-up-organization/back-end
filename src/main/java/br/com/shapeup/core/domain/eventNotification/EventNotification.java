package br.com.shapeup.core.domain.eventNotification;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.Objects;

public class EventNotification extends Entity<EventNotificationId> {
    private String title;
    
    private String message;
    
    private String eventId;

    private EventNotification(EventNotificationId id,String title, String message, String eventId) {
        super(id);
        this.title = title;
        this.message = message;
        this.eventId = eventId;
    }

    public static EventNotification newEventNotification(EventNotificationId id,String title, String Message, String eventId) {
        return new EventNotification(id, title, Message, eventId);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new EventNotificationValidator(handler, this).validate();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EventNotification that = (EventNotification) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMessage(), getTitle());
    }
}
