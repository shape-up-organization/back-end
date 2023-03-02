package br.com.shapeup.common.exceptions;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiErrorMessage {
    private HttpStatus httpStatus;
    private List<String> errors;

    public ApiErrorMessage(HttpStatus httpStatus, List<String> errors) {
        super();
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

    public ApiErrorMessage(HttpStatus httpStatus, String error) {
        super();
        this.httpStatus = httpStatus;
        this.errors = Arrays.asList(error);
    }
}
