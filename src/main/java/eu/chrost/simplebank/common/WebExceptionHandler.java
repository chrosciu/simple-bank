package eu.chrost.simplebank.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    record ErrorResponse(String message) {}
}
