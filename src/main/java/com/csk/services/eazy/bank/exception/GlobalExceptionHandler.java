package com.csk.services.eazy.bank.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exception(Exception e) {

        var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<Error> exception(BusinessValidationException e) {

        var error = new Error(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), e.getTarget());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Error> exception(AccessDeniedException e) {

        var error = new Error(HttpStatus.FORBIDDEN.toString(), e.getMessage(), null);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Error {

        private String errorCode;
        private String errorMessage;
        private String target;
    }
}
