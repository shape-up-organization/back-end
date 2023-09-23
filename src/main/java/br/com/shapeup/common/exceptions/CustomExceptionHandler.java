package br.com.shapeup.common.exceptions;

import br.com.shapeup.common.exceptions.auth.register.UsernameInUseException;
import br.com.shapeup.common.exceptions.comment.CommentIsNotYours;
import br.com.shapeup.common.exceptions.comment.CommentNotFoundException;
import br.com.shapeup.common.exceptions.friend.AddYourselfAsAFriendException;
import br.com.shapeup.common.exceptions.friend.AlreadyFriendException;
import br.com.shapeup.common.exceptions.friend.AlreadySentFriendRequestException;
import br.com.shapeup.common.exceptions.friend.DeleteYourselfAsAFriendException;
import br.com.shapeup.common.exceptions.friend.DuplicateFriendshipException;
import br.com.shapeup.common.exceptions.friend.FriendshipRequestAlreadyAcceptedException;
import br.com.shapeup.common.exceptions.friend.FriendshipRequestAlreadyExistsException;
import br.com.shapeup.common.exceptions.friend.FriendshipRequestNotFoundException;
import br.com.shapeup.common.exceptions.friend.NotFriendException;
import br.com.shapeup.common.exceptions.post.PostNotFoundException;
import br.com.shapeup.common.exceptions.profile.ProfilePictureNotFoundException;
import br.com.shapeup.common.exceptions.quest.InsufficientXPException;
import br.com.shapeup.common.exceptions.server.InternalServerErrorException;
import br.com.shapeup.common.exceptions.user.InvalidCredentialException;
import br.com.shapeup.common.exceptions.user.UserExistsByCellPhoneException;
import br.com.shapeup.common.exceptions.user.UserInvalidBirthException;
import br.com.shapeup.common.exceptions.user.UserInvalidCellPhoneException;
import br.com.shapeup.common.exceptions.user.UserInvalidEmailException;
import br.com.shapeup.common.exceptions.user.UserInvalidLastName;
import br.com.shapeup.common.exceptions.user.UserInvalidNameException;
import br.com.shapeup.common.exceptions.user.UserInvalidPasswordException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.domain.verification.exception.InvalidCodeException;
import com.amazonaws.services.elasticache.model.UserAlreadyExistsException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        var apiErrorMessage = new ApiErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST, request.getRequestURI(),
                errors
        );

        return ResponseEntity.badRequest().body(apiErrorMessage);
    }


    @ExceptionHandler({
            UserNotFoundException.class,
            FriendshipRequestNotFoundException.class,
            ProfilePictureNotFoundException.class,
            PostNotFoundException.class,
            CommentNotFoundException.class,
            ShapeUpNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundException(
            Exception exception,
            HttpServletRequest request
    ) {
        var apiErrorMessage = new ApiErrorMessage(HttpServletResponse.SC_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                request.getRequestURI(),
                exception.getMessage()
        );

        return new ResponseEntity<>(
                apiErrorMessage,
                new HttpHeaders(),
                apiErrorMessage.getHttpStatus()
        );
    }

    @ExceptionHandler({
            InvalidCredentialException.class,
            AlreadySentFriendRequestException.class,
            UsernameInUseException.class,
            FriendshipRequestAlreadyExistsException.class
    })
    public ResponseEntity<Object> handleConflictException(
            Exception exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        var apiErrorMessage = new ApiErrorMessage(
                HttpServletResponse.SC_CONFLICT,
                HttpStatus.CONFLICT,
                request.getRequestURI(),
                exception.getMessage()
        );

        return new ResponseEntity<>(
                apiErrorMessage,
                new HttpHeaders(),
                apiErrorMessage.getHttpStatus()
        );
    }

    @ExceptionHandler({
            UserInvalidPasswordException.class,
            UserInvalidEmailException.class,
            UserInvalidBirthException.class,
            UserInvalidCellPhoneException.class,
            UserInvalidLastName.class,
            UserInvalidNameException.class,
            UserExistsByCellPhoneException.class,
            ExpiredJwtException.class,
            UserAlreadyExistsException.class,
            NotFriendException.class,
            DeleteYourselfAsAFriendException.class,
            AddYourselfAsAFriendException.class,
            CommentIsNotYours.class,
            InsufficientXPException.class,
            InvalidCodeException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadRequestException(
            Exception exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        var apiErrorMessage = new ApiErrorMessage(
                HttpServletResponse.SC_BAD_REQUEST,
                HttpStatus.BAD_REQUEST,
                request.getRequestURI(),
                exception.getMessage()
        );

        return new ResponseEntity<>(
                apiErrorMessage,
                new HttpHeaders(),
                apiErrorMessage.getHttpStatus()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String message = String.format("Data integrity error: %s", exception.getMessage());
        HttpStatus status = HttpStatus.CONFLICT;

        var apiErrorMessage = new ApiErrorMessage(
                status.value(),
                status,
                request.getRequestURI(),
                message
        );

        return new ResponseEntity<>(
                apiErrorMessage,
                new HttpHeaders(),
                apiErrorMessage.getHttpStatus()
        );
    }

    @ExceptionHandler({
            Exception.class,
            InternalServerErrorException.class
    })
    public ResponseEntity<Object> handleInternalServerError(
            Exception exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        var apiErrorMessage = new ApiErrorMessage(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getRequestURI(),
                exception.getMessage()
        );

        return new ResponseEntity<>(
                apiErrorMessage,
                new HttpHeaders(),
                apiErrorMessage.getHttpStatus()
        );
    }

    @ExceptionHandler({
            AlreadyFriendException.class,
            FriendshipRequestAlreadyAcceptedException.class,
            DuplicateFriendshipException.class
    })
    public ResponseEntity<Object> handleConflictRequestException(
            Exception exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        var apiErrorMessage = new ApiErrorMessage(
                HttpServletResponse.SC_CONFLICT,
                HttpStatus.CONFLICT,
                request.getRequestURI(),
                exception.getMessage()
        );

        return new ResponseEntity<>(
                apiErrorMessage,
                new HttpHeaders(),
                apiErrorMessage.getHttpStatus()
        );
    }
}
