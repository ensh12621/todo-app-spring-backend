package com.kkh.todoapp.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kkh.todoapp.entity.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long>{
    Optional<TodoEntity> findByIdx(Long idx);
    
}