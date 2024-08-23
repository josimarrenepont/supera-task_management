package controller.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import service.exceptions.DatabaseExceptions;
import service.exceptions.ResourceNotFoundExceptions;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionsHandler {
    @ExceptionHandler(ResourceNotFoundExceptions.class)
    public ResponseEntity<StandardError> exceptionHandler(ResourceNotFoundExceptions e, HttpServletRequest request){

        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.ok().body(err);
    }
    @ExceptionHandler(DatabaseExceptions.class)
    public ResponseEntity<StandardError> database(DatabaseExceptions e, HttpServletRequest request){

        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError dataError = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.ok().body(dataError);
    }
}
