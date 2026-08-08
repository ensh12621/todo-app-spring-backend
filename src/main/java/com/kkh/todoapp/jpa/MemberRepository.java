package com.kkh.todoapp.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkh.todoapp.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer>{
    Optional<MemberEntity> findByEmail(String email);
    Optional<MemberEntity> findByEmailAndPassword(String email, String password);
}
