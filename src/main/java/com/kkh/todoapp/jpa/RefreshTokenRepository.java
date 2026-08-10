package com.kkh.todoapp.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkh.todoapp.entity.RefreshTokenEntity;


public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer>{
    Optional<RefreshTokenEntity> findByToken(String token);
}
