package com.kkh.todoapp.service;

import java.util.List;

import com.kkh.todoapp.entity.TodoEntity;
import com.kkh.todoapp.vo.TodoVO;

public interface TodoService {
       List<TodoEntity> getMemoList();
       boolean save(TodoVO vo);              
}
