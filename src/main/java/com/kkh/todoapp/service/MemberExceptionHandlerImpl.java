package com.kkh.todoapp.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.kkh.todoapp.exception.AheadOfExpirationException;
import com.kkh.todoapp.exception.UnauthorizedException;

@Component
public class MemberExceptionHandlerImpl implements MemberExceptionHandler{

    @Override
    public BadCredentialsException badCredentailException() {   
        return new BadCredentialsException("no user found");
    }

    @Override
    public AheadOfExpirationException aheadOfExpirationException() {
        return new AheadOfExpirationException("refresh token was expired");
    }
    
    @Override
    public UnauthorizedException unauthorizedException() {
        return new UnauthorizedException("unauthorized");
    }
}
