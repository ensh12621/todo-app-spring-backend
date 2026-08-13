package com.kkh.todoapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkh.todoapp.entity.TodoEntity;
import com.kkh.todoapp.service.TodoService;
import com.kkh.todoapp.vo.SearchTitleDTO;
import com.kkh.todoapp.vo.TodoVO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/save-new-todo")
    public ResponseEntity<TodoVO> postMethodName(@RequestBody TodoVO vo) {
        logger.info("신규 메모 등록 - ({}) / ({})", vo.getTitle(), vo.getContent());
        todoService.save(vo);
        return ResponseEntity.ok(vo);
    }

    @GetMapping("/get-todo-list")
    public List<TodoEntity> getTodoList() {
        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication();
        // logger.info("authentication with " + authentication.getPrincipal().toString()
        // + ", roles: " + authentication.getAuthorities());

        return todoService.getMemoList();
    }

    @GetMapping("/search-by-title")
    public List<TodoEntity> searchByTitle(@RequestBody SearchTitleDTO searchDTO) {
        return todoService.findByTitle(searchDTO.getKeyword());
    }

}
