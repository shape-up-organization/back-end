package br.com.shapeup.common.exceptions;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiErrorMessage {
    private int status;
    private HttpStatus error;
    private String path;
    private List<String> errors;

    public ApiErrorMessage(int status, HttpStatus error, String path, List<String> errors) {
        super();
        this.status = status;
        this.error = error;
        this.path = path;
        this.errors = errors;
    }

    public ApiErrorMessage(int status, HttpStatus httpStatus, String path, String error) {
        super();
        this.status = status;
        this.error = httpStatus;
        this.path = path;
        this.errors = Arrays.asList(error);
    }
}
