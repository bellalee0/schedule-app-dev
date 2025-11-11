package com.example.scheduleappdev.todo.controller;

import com.example.scheduleappdev.todo.dto.*;
import com.example.scheduleappdev.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 전체 일정 조회하기
     *
     * @return 200 OK 상태코드와 조회된 내용 반환
     */
    @GetMapping("/todos")
    public ResponseEntity<List<GetTodoResponse>> getAllTodos() {
        List<GetTodoResponse> result = todoService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 일정 조회하기
     *
     * @param todoId API Path로 일정 ID 선택받기
     * @return 200 OK 상태코드와 조회된 내용 반환
     */
    @GetMapping("/todos/{todo_id}")
    public ResponseEntity<GetTodoResponse> getOneTodo(@PathVariable Long todoId) {
        GetTodoResponse result = todoService.getOne(todoId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 일정 수정하기
     *
     * @param todoId API Path로 일정 ID 선택받기
     * @param request HTTP Body로 내용 받기
     * @return 200 OK 상태코드와 수정된 내용 반환
     */
    @PutMapping("/todos/{todo_id}")
    public ResponseEntity<UpdateTodoResponse> updateTodo(@PathVariable Long todoId, @RequestBody UpdateTodoRequest request) {
        UpdateTodoResponse result = todoService.update(todoId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 일정 삭제하기
     *
     * @param todoId API Path로 일정 ID 선택받기
     * @return 204 NO_CONTENT 상태코드 반환
     */
    @DeleteMapping("/todos/{todo_id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId) {
        todoService.delete(todoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
