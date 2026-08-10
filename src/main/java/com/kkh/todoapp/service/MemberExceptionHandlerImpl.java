package com.kkh.todoapp.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

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
    
}
