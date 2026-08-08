package com.kkh.todoapp.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkh.todoapp.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long>{
    Optional<TodoEntity> findByIdx(Long idx);
    
}