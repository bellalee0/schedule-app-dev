package com.example.scheduleappdev.todo.service;

import com.example.scheduleappdev.todo.dto.*;
import com.example.scheduleappdev.todo.entity.Todo;
import com.example.scheduleappdev.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    /**
     * 일정 생성하기
     *
     * @param request 내용 받기
     * @return 생성된 내용 DTO에 담아 반환
     */
    @Transactional
    public CreateTodoResponse create(CreateTodoRequest request) {
        Todo todo = new Todo(
                request.getCreator(), request.getTitle(), request.getContents()
        );
        Todo savedTodo = todoRepository.save(todo);
        return new CreateTodoResponse(
                savedTodo.getId(), savedTodo.getTitle(), savedTodo.getContents(), savedTodo.getCreator(), savedTodo.getCreatedAt(), savedTodo.getModifiedAt()
        );
    }

    /**
     * 전체 일정 조회하기
     *
     * @return 저장된 일정 DTO에 담아 List로 반환
     */
    @Transactional(readOnly = true)
    public List<GetTodoResponse> getAll() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                .map(todo -> new GetTodoResponse(
                    todo.getId(), todo.getTitle(), todo.getContents(), todo.getCreator(), todo.getCreatedAt(), todo.getModifiedAt()
                )).toList();
    }
}
