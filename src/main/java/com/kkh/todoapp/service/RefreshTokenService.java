package com.kkh.todoapp.service;

import com.kkh.todoapp.entity.RefreshTokenEntity;

public interface RefreshTokenService {
    RefreshTokenEntity generateRefreshToken(String email);
    String validateRefreshToken(String refreshToken);
}
