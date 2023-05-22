package br.com.shapeup.core.domain.eventNotification;

import br.com.shapeup.common.exceptions.event.InvalidEventNotificationException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;

public class EventNotificationValidator extends Validator {
    private final EventNotification eventNotification;

    protected EventNotificationValidator(ValidationHandler handler, EventNotification eventNotification) {
        super(handler);
        this.eventNotification = eventNotification;
    }
    
    public void validate() {
        validateTitle();
        validateMessage();
        validateEventId();
    }

    private void validateTitle() {
        if (eventNotification.getTitle().isEmpty()) {
            throw new InvalidEventNotificationException("Title is empty");
        }
    }
    
    private void validateMessage() {
        if (eventNotification.getMessage().isEmpty()) {
            throw new InvalidEventNotificationException("Message is empty");
        }
    }
    
    private void validateEventId() {
        if (eventNotification.getEventId().isEmpty()) {
            throw new InvalidEventNotificationException("Id event is empty");
        }
    }
}
