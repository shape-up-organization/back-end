package br.com.shapeup.common.exceptions.event;

public class InvalidEventNotificationException extends RuntimeException{
    public InvalidEventNotificationException(String message) {
        super(message);
    }
}
