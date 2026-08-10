package com.kkh.todoapp.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.kkh.todoapp.entity.TodoEntity;
import com.kkh.todoapp.jpa.TodoRepository;
import com.kkh.todoapp.vo.TodoVO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TodoServiceImpl implements TodoService{
    
    private TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @Override
    public List<TodoEntity> getMemoList() {
        return todoRepository.findAll();
    }

    @Override
    public boolean save(TodoVO vo) {
        TodoEntity entity = new TodoEntity(vo.getTitle(), vo.getContent());
        TodoEntity saved = todoRepository.save(entity);
        return saved != null ? true : false;       
    }

    @PreAuthorize("hasRole('TESTER')")
    @Override
    public String test() {
        return "200";
    }

    @Override
    public List<TodoEntity> findByTitle(String keyword) {
        return todoRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
