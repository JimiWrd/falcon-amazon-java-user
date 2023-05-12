package com.jumar.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserCreateFailedException.class)
    public ResponseEntity<ErrorObject> handleUserCreateFailedException(UserCreateFailedException ex, WebRequest request) {
        ErrorObject error = new ErrorObject();
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(OneOrMoreUserRequiredFieldsNullException.class)
    public ResponseEntity<ErrorObject> handleOneOrMoreUserRequiredFieldsNullException(OneOrMoreUserRequiredFieldsNullException ex) {
        ErrorObject error = new ErrorObject();
        error.setStatusCode(null);
        error.setMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());

        return ResponseEntity.badRequest().body(error);
    }

}
