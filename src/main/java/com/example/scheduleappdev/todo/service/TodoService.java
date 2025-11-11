package com.example.scheduleappdev.todo.service;

import com.example.scheduleappdev.todo.dto.*;
import com.example.scheduleappdev.todo.entity.Todo;
import com.example.scheduleappdev.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    /**
     * 선택 일정 조회하기
     *
     * @param todo_id 일정 ID 받기
     * @return 조회된 일정 DTO에 담아 반환
     * @throws IllegalStateException 존재하지 않는 일정 ID 입력 시
     */
    @Transactional(readOnly = true)
    public GetTodoResponse getOne(Long todo_id) {
        Todo todo = todoRepository.findById(todo_id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 일정 ID입니다."));
        return new GetTodoResponse(
                todo.getId(), todo.getTitle(), todo.getContents(), todo.getCreator(), todo.getCreatedAt(), todo.getModifiedAt()
        );
    }

    /**
     * 선택 일정 수정하기
     *
     * @param todo_id 일정 ID 받기
     * @param request 수정할 내용 받기(일정 제목, 작성자명)
     * @return 수정된 내용 DTO에 담아 반환
     * @throws IllegalStateException 존재하지 않는 일정 ID 입력 시
     */
    @Transactional
    public UpdateTodoResponse update(Long todo_id, UpdateTodoRequest request) {
        Todo todo = todoRepository.findById(todo_id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 일정 ID입니다."));
        todo.update(request.getTitle(), request.getCreator());
        todoRepository.saveAndFlush(todo);
        return new UpdateTodoResponse(
                todo.getId(), todo.getTitle(), todo.getContents(), todo.getCreator(), todo.getCreatedAt(), todo.getModifiedAt()
        );
    }

    /**
     * 선택 일정 삭제하기
     *
     * @param todo_id API Path로 일정 ID 선택받기
     * @throws IllegalStateException 존재하지 않는 일정 ID 입력 시
     */
    @Transactional
    public void delete(Long todo_id) {
        boolean exists = todoRepository.existsById(todo_id);
        if (!exists) { throw new IllegalStateException("존재하지 않는 일정 ID입니다."); }
        todoRepository.deleteById(todo_id);
    }
}
