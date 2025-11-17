package com.example.scheduleappdev.todo.controller;

import com.example.scheduleappdev.global.Exception.ErrorMessage;
import com.example.scheduleappdev.global.Exception.TodoServiceException;
import com.example.scheduleappdev.todo.dto.*;
import com.example.scheduleappdev.todo.service.TodoService;
import com.example.scheduleappdev.user.dto.UserForHttpSession;
import jakarta.validation.Valid;
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
     * @param sessionUser loginUser 이름을 가진 세션 속성 찾아 DTO에 주입
     * @param request HTTP Body로 내용 받기
     * @return 201 CREATED 상태코드와 생성된 내용 반환
     * @throws TodoServiceException 비로그인 상태로 접근 시 Not_Logged_In 예외 발생
     */
    @PostMapping("/todos")
    public ResponseEntity<CreateTodoResponse> createTodo(
            @SessionAttribute(name = "loginUser", required = false) UserForHttpSession sessionUser,
            @Valid @RequestBody CreateTodoRequest request) {
        if (sessionUser == null) { throw new TodoServiceException(ErrorMessage.NOT_LOGGED_IN); }
        CreateTodoResponse result = todoService.create(request, sessionUser.getId());
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
    @GetMapping("/todos/{todoId}")
    public ResponseEntity<GetOneTodoResponse> getOneTodo(@PathVariable Long todoId) {
        GetOneTodoResponse result = todoService.getOne(todoId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 일정 수정하기
     *
     * @param sessionUser loginUser 이름을 가진 세션 속성 찾아 DTO에 주입
     * @param todoId API Path로 일정 ID 선택받기
     * @param request HTTP Body로 내용 받기
     * @return 200 OK 상태코드와 수정된 내용 반환
     * @throws TodoServiceException 비로그인 상태로 접근 시 Not_Logged_In 예외 발생
     */
    @PutMapping("/todos/{todoId}")
    public ResponseEntity<UpdateTodoResponse> updateTodo(
            @SessionAttribute(name = "loginUser", required = false) UserForHttpSession sessionUser,
            @PathVariable Long todoId,
            @Valid @RequestBody UpdateTodoRequest request) {
        if (sessionUser == null) { throw new TodoServiceException(ErrorMessage.NOT_LOGGED_IN); }
        UpdateTodoResponse result = todoService.update(todoId, request, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 일정 삭제하기
     *
     * @param sessionUser loginUser 이름을 가진 세션 속성 찾아 DTO에 주입
     * @param todoId API Path로 일정 ID 선택받기
     * @return 204 NO_CONTENT 상태코드 반환
     * @throws TodoServiceException 비로그인 상태로 접근 시 Not_Logged_In 예외 발생
     */
    @DeleteMapping("/todos/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @SessionAttribute(name = "loginUser", required = false) UserForHttpSession sessionUser,
            @PathVariable Long todoId) {
        if (sessionUser == null) { throw new TodoServiceException(ErrorMessage.NOT_LOGGED_IN); }
        todoService.delete(todoId, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
