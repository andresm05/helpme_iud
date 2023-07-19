package co.edu.iudigital.helpmeiud.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.edu.iudigital.helpmeiud.exceptions.EntityNotFoundException;
import co.edu.iudigital.helpmeiud.exceptions.ErrorMessage;
import co.edu.iudigital.helpmeiud.exceptions.NoValidUsernameException;
import co.edu.iudigital.helpmeiud.exceptions.NotEnabledUserException;
import co.edu.iudigital.helpmeiud.exceptions.UnauthorizedException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception exception, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timestamp(new Date())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(
            EntityNotFoundException exception,
            WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timestamp(new Date())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoValidUsernameException.class)
    public ResponseEntity<ErrorMessage> noValidUsernameException(
            NoValidUsernameException exception,
            WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timestamp(new Date())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessage> unauthorizedException(
            UnauthorizedException exception,
            WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timestamp(new Date())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(exception.getMessage())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotEnabledUserException.class)
    public ResponseEntity<ErrorMessage> notEnabledUserException(
            NotEnabledUserException exception,
            WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timestamp(new Date())
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message(exception.getMessage())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.FORBIDDEN);
    }

}
