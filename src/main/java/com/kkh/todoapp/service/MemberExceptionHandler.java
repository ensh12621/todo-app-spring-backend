package com.kkh.todoapp.service;

import org.springframework.security.authentication.BadCredentialsException;

public interface MemberExceptionHandler {    
    public BadCredentialsException badCredentailException();
    public AheadOfExpirationException aheadOfExpirationException();
}
