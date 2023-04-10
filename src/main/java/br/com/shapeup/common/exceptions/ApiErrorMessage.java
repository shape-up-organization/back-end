package br.com.shapeup.common.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;

public class ApiErrorMessage {

    private final String ZONE_ID = "America/Sao_Paulo";

    private int statusCode;
    private HttpStatus httpStatus;
    private String path;
    private List<String> messages;
    private ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of(ZONE_ID));


    public ApiErrorMessage(int status, HttpStatus httpStatus, String path, List<String> messages) {
        super();
        this.statusCode = status;
        this.httpStatus = httpStatus;
        this.path = path;
        this.messages = messages;
        this.timestamp = ZonedDateTime.now(ZoneId.of(ZONE_ID));
    }

    public ApiErrorMessage(int status, HttpStatus httpStatus, String path, String error) {
        super();
        this.statusCode = status;
        this.httpStatus = httpStatus;
        this.path = path;
        this.messages = Arrays.asList(error);
        this.timestamp = ZonedDateTime.now(ZoneId.of(ZONE_ID));
    }

    public ApiErrorMessage(HttpStatus httpStatus, List<String> messages) {
        super();
        this.httpStatus = httpStatus;
        this.messages = messages;
        this.timestamp = ZonedDateTime.now(ZoneId.of(ZONE_ID));
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getPath() {
        return path;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z");
        return timestamp.format(formatter);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
