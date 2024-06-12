package com.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> getEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setMessage(e.getMessage());
        response.setErrorCode(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> getEntityNotFoundException(EntityNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setMessage(e.getMessage());
        response.setErrorCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
