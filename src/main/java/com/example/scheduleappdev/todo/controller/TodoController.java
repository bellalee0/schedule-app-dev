package com.example.scheduleappdev.todo.controller;

import com.example.scheduleappdev.todo.dto.*;
import com.example.scheduleappdev.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    /**
     * 일정 생성하기
     *
     * @param request HTTP Body로 내용 받기
     * @return 201 CREATED 상태코드와 생성된 내용 반환
     */
    @PostMapping("/todos")
    public ResponseEntity<CreateTodoResponse> createTodo(@RequestBody CreateTodoRequest request) {
        CreateTodoResponse result = todoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
