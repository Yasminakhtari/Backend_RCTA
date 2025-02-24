package com.ifacehub.tennis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ifacehub.tennis.util.ResponseObject;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseObject> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
            new ResponseObject(HttpStatus.NOT_FOUND, ex.getMessage(), null),
            HttpStatus.NOT_FOUND
        );
    }
}