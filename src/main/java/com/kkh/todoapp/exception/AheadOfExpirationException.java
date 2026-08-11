package com.kkh.todoapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AheadOfExpirationException extends RuntimeException{
    
    public AheadOfExpirationException(String msg){
        super(msg);
    }
}
