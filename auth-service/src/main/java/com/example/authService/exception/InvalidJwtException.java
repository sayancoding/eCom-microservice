package com.example.authService.exception;

public class InvalidJwtException extends RuntimeException{
    public InvalidJwtException(String msg){
        super(msg);
    }
    public InvalidJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
