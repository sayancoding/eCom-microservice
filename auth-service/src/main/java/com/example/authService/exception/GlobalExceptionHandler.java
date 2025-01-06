package com.example.authService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({NoUserException.class})
    public ResponseEntity<String> noUserExceptionHandler(String msg){
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

}
