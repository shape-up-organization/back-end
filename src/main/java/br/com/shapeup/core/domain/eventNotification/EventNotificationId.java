package br.com.shapeup.core.domain.eventNotification;

import br.com.shapeup.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class EventNotificationId extends Identifier<String> {
    private final String value;

    private EventNotificationId(String value) {
        super(value);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static EventNotificationId unique() {
        return EventNotificationId.from(UUID.randomUUID());
    }

    public static EventNotificationId from(final String anId) {
        return new EventNotificationId(anId);
    }

    public static EventNotificationId from(final UUID anId) {
        return new EventNotificationId(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        EventNotificationId eventNotificationId = (EventNotificationId) o;
        return getValue().equals(eventNotificationId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
