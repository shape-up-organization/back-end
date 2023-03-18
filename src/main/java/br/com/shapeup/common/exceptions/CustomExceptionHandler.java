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
import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

        var apiErrorMessage = new ApiErrorMessage(status, errors);
        return new ResponseEntity<>(apiErrorMessage, apiErrorMessage.getHttpStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception, WebRequest request) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }

    @ExceptionHandler(UserExistsByEmailException.class)
    public ResponseEntity<Object> handleUserExistsByEmailException(UserExistsByEmailException exception,
            WebRequest request) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }

    @ExceptionHandler(UserInvalidCellPhoneException.class)
    public ResponseEntity<Object> handleUserInvalidCellPhoneException(UserInvalidCellPhoneException exception,
                                                                      WebRequest request){
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }

    @ExceptionHandler(UserInvalidBirthException.class)
    public ResponseEntity<Object> handleUserInvalidBirthException(UserInvalidBirthException exception,
                                                                  WebRequest request){
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }

    @ExceptionHandler(UserInvalidEmailException.class)
    public ResponseEntity<Object> handleUserInvalidEmailException(UserInvalidEmailException exception,
                                                                  WebRequest request){
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }

    @ExceptionHandler(UserInvalidPasswordException.class)
    public ResponseEntity<Object> handleUserInvalidPasswordException(UserInvalidPasswordException exception,
                                                                     WebRequest request){
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }

    @ExceptionHandler(UserExistsByCellPhoneException.class)
    public ResponseEntity<Object> handlerUserExistsByCellPhoneException(UserExistsByCellPhoneException exception,
                                                                   WebRequest request){
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }

    @ExceptionHandler(UserInvalidNameException.class)
    public ResponseEntity<Object> handlerUserInvalidNameException(UserInvalidNameException exception,
                                                                  WebRequest request) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }

    @ExceptionHandler(UserInvalidLastName.class)
    public ResponseEntity<Object> handlerUserInvalidLastName(UserInvalidLastName exception,
                                                             WebRequest request) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }
}
