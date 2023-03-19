package br.com.shapeup.common.exceptions;

import br.com.shapeup.common.exceptions.user.UserExistsByCellPhoneException;
import br.com.shapeup.common.exceptions.user.UserExistsByEmailException;
import br.com.shapeup.common.exceptions.user.UserInvalidBirthException;
import br.com.shapeup.common.exceptions.user.UserInvalidCellPhoneException;
import br.com.shapeup.common.exceptions.user.UserInvalidEmailException;
import br.com.shapeup.common.exceptions.user.UserInvalidLastName;
import br.com.shapeup.common.exceptions.user.UserInvalidNameException;
import br.com.shapeup.common.exceptions.user.UserInvalidPasswordException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidArgument(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    errors.add(error.getDefaultMessage()
                    );
                });
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, request.getRequestURI(), (List<String>) errors);
        return ResponseEntity.badRequest().body(apiErrorMessage);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException exception,
            HttpServletRequest request
    ) {
        var apiErrorMessage = new ApiErrorMessage(HttpServletResponse.SC_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                request.getRequestURI(),
                exception.getMessage()
        );
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }

    @ExceptionHandler(UserExistsByEmailException.class)
    public ResponseEntity<Object> handleUserExistsByEmailException(
            UserExistsByEmailException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        var apiErrorMessage = new ApiErrorMessage(
                HttpServletResponse.SC_CONFLICT,
                HttpStatus.CONFLICT,
                request.getRequestURI(),
                exception.getMessage()
        );
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }

    @ExceptionHandler({
            UsernameNotFoundException.class,
            UserInvalidPasswordException.class,
            UserInvalidEmailException.class,
            UserInvalidBirthException.class,
            UserInvalidCellPhoneException.class,
            UserInvalidLastName.class,
            UserInvalidNameException.class,
            UserExistsByCellPhoneException.class
    })
    public ResponseEntity<Object> handleUsernameNotFoundException(
            UsernameNotFoundException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        var apiErrorMessage = new ApiErrorMessage(
                HttpServletResponse.SC_BAD_REQUEST,
                HttpStatus.BAD_REQUEST,
                request.getRequestURI(),
                exception.getMessage()
        );
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }
}
