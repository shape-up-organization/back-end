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
import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            HttpServletRequest request
    ) {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

        var apiErrorMessage = new ApiErrorMessage(HttpServletResponse.SC_BAD_REQUEST, status, request.getRequestURI(), errors);
        request.getContextPath();
        return new ResponseEntity<>(apiErrorMessage, apiErrorMessage.getError());
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException exception,
            HttpServletRequest request
    ) {
        var apiErrorMessage = new ApiErrorMessage(HttpServletResponse.SC_NOT_FOUND, HttpStatus.NOT_FOUND, request.getRequestURI(), exception.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getError());
    }

    @ExceptionHandler(UserExistsByEmailException.class)
    public ResponseEntity<Object> handleUserExistsByEmailException(
            UserExistsByEmailException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        var apiErrorMessage = new ApiErrorMessage(HttpServletResponse.SC_CONFLICT, HttpStatus.CONFLICT, request.getRequestURI(), exception.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getError());
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
        var apiErrorMessage = new ApiErrorMessage(HttpServletResponse.SC_BAD_REQUEST, HttpStatus.BAD_REQUEST, request.getRequestURI(), exception.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getError());
    }
}
