package com.kkh.todoapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kkh.todoapp.exception.AheadOfExpirationException;
import com.kkh.todoapp.exception.UnauthorizedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AheadOfExpirationException.class )
    public ResponseEntity<ErrorResponse> handleAheadOfExpirationException(AheadOfExpirationException e){
        logger.info("핸들러 1 -");
        ErrorResponse response = ErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message("expired")
                        .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class )
    public ResponseEntity<ErrorResponse> UnauthorizedException(UnauthorizedException e){
        logger.info("핸들러 2 -");
        ErrorResponse response = ErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message("unauthorized")
                        .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    
}


