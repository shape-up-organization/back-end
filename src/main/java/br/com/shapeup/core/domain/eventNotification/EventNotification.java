package br.com.shapeup.core.domain.eventNotification;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import com.amazonaws.services.dynamodbv2.xspec.M;

import java.util.Objects;

public class EventNotification extends Entity<EventNotificationId> {
    private String title;
    private String message;

    public EventNotification( String title, String message) {
        super(EventNotificationId.unique());
        this.title = title;
        this.message = message;
    }
    public static EventNotification newEventNotification(String title, String Message){
        return newEventNotification(title, Message);
    }

    @Override
    public void validate(ValidationHandler handler) {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EventNotification that = (EventNotification) o;

        if (!Objects.equals(title, that.title)) return false;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
