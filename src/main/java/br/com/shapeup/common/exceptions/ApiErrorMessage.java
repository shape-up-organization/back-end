package br.com.shapeup.common.exceptions;

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
    private List<String> errors;

    public ApiErrorMessage(int statusCode, HttpStatus httpStatus, String path, List<String> errors) {
        super();
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.path = path;
        this.errors = errors;
    }

    public ApiErrorMessage(int statusCode, HttpStatus httpStatus, String path, String error) {
        super();
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.path = path;
        this.errors = Arrays.asList(error);
    }
}
