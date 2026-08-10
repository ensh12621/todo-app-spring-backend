package com.kkh.todoapp.service;

public class AheadOfExpirationException extends RuntimeException{
    
    public AheadOfExpirationException(String msg){
        super(msg);
    }
}
