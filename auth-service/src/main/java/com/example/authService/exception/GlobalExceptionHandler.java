package com.example.authService.exception;

import com.example.authService.dto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({NoUserException.class})
    public ResponseEntity<ErrorResponse> noUserExceptionHandler(NoUserException ex){
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(),null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidJwtException.class})
    public ResponseEntity<ErrorResponse> jwtExceptionHandler(InvalidJwtException ex){
        return new ResponseEntity<>(new ErrorResponse("Invalid Token",ex.getCause().getMessage()), HttpStatus.UNAUTHORIZED);
    }

}
