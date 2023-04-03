package br.com.shapeup.common.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiErrorMessage {
    private int statusCode;
    private HttpStatus httpStatus;
    private String path;
    private List<String> messages;
    private ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));


    public ApiErrorMessage(int status, HttpStatus httpStatus, String path, List<String> messages) {
        super();
        this.statusCode = status;
        this.httpStatus = httpStatus;
        this.path = path;
        this.messages = messages;
    }

    public ApiErrorMessage(int status, HttpStatus httpStatus, String path, String error) {
        super();
        this.statusCode = status;
        this.httpStatus = httpStatus;
        this.path = path;
        this.messages = Arrays.asList(error);
    }

    public ApiErrorMessage(HttpStatus httpStatus, List<String> messages) {
        super();
        this.httpStatus = httpStatus;
        this.messages = messages;
    }
}
