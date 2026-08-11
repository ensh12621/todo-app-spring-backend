package com.kkh.todoapp.service;

import org.springframework.security.authentication.BadCredentialsException;

import com.kkh.todoapp.exception.AheadOfExpirationException;
import com.kkh.todoapp.exception.UnauthorizedException;


public interface MemberExceptionHandler {    
    public BadCredentialsException badCredentailException();
    public AheadOfExpirationException aheadOfExpirationException();
    public UnauthorizedException unauthorizedException();
}
