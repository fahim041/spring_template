package com.jdbc_template.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.LocalTime;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler
    public ResponseEntity<GlobalErrorResponse> handleException(Exception ex){
        GlobalErrorResponse error = new GlobalErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<GlobalErrorResponse> handleException(DuplicateEmailException duplicateEmailException){
        GlobalErrorResponse error = new GlobalErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(duplicateEmailException.getMessage());
        error.setTimestamp(Instant.now().toEpochMilli());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<GlobalErrorResponse> handleException(NotFoundException ex){
        GlobalErrorResponse error = new GlobalErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
