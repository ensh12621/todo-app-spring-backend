package com.kkh.todoapp.service;

import java.util.Date;

import com.kkh.todoapp.vo.LoginDTO;

public interface JwtService {
    String generateJwt(LoginDTO loginDTO);
    String extractSubject(String token);
    Date extractExpirationDate(String token);
    boolean validateToken(String token);
}
