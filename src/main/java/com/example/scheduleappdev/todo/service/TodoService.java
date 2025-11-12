package com.example.scheduleappdev.todo.service;

import com.example.scheduleappdev.todo.dto.*;
import com.example.scheduleappdev.todo.entity.Todo;
import com.example.scheduleappdev.todo.repository.TodoRepository;
import com.example.scheduleappdev.user.entity.User;
import com.example.scheduleappdev.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    /**
     * 일정 생성하기
     *
     * @param request 내용 받기(일정 제목, 내용)
     * @param userId Http Session을 통해 유저ID 받기
     * @return 생성된 내용 DTO에 담아 반환
     * @throws IllegalStateException 존재하지 않는 유저로 접근 시
     */
    @Transactional
    public CreateTodoResponse create(CreateTodoRequest request, Long userId) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        Todo todo = new Todo(
                request.getTitle(), request.getContents(), creator
        );
        Todo savedTodo = todoRepository.save(todo);
        return new CreateTodoResponse(
                savedTodo.getId(), savedTodo.getTitle(), savedTodo.getContents(), savedTodo.getCreator().getUsername(), savedTodo.getCreatedAt(), savedTodo.getModifiedAt()
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
                    todo.getId(), todo.getTitle(), todo.getContents(), todo.getCreator().getUsername(), todo.getCreatedAt(), todo.getModifiedAt()
                )).toList();
    }

    /**
     * 선택 일정 조회하기
     *
     * @param todoId 일정 ID 받기
     * @return 조회된 일정 DTO에 담아 반환
     * @throws IllegalStateException 존재하지 않는 일정 ID 입력 시
     */
    @Transactional(readOnly = true)
    public GetTodoResponse getOne(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 일정 ID입니다."));
        return new GetTodoResponse(
                todo.getId(), todo.getTitle(), todo.getContents(), todo.getCreator().getUsername(), todo.getCreatedAt(), todo.getModifiedAt()
        );
    }

    /**
     * 선택 일정 수정하기
     *
     * @apiNote 1. 일정ID로 일정 찾기(없으면 예외 처리) / 2. 일정의 유저ID와 로그인된 유저의 ID 일치 여부 확인(불일치시 예외 처리) / 3. 일정 수정
     * @param todoId 일정 ID 받기
     * @param request 수정할 내용 받기(일정 제목)
     * @param userId Http Session을 통해 유저ID 받기
     * @return 수정된 내용 DTO에 담아 반환
     * @throws IllegalStateException 존재하지 않는 일정 ID 입력 시
     * @throws IllegalStateException 수정하려는 일정의 유저ID와 로그인한 유저의 ID 불일치 시
     * @throws IllegalStateException 존재하지 않는 유저로 접근 시
     */
    @Transactional
    public UpdateTodoResponse update(Long todoId, UpdateTodoRequest request, Long userId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 일정 ID입니다."));
        if (!todo.getCreator().getId().equals(userId)) { throw new IllegalStateException("접근 권한이 없습니다."); }
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        todo.update(request.getTitle(), creator);
        todoRepository.saveAndFlush(todo);
        return new UpdateTodoResponse(
                todo.getId(), todo.getTitle(), todo.getContents(), todo.getCreator().getUsername(), todo.getCreatedAt(), todo.getModifiedAt()
        );
    }

    /**
     * 선택 일정 삭제하기
     *
     * @param todoId API Path로 일정 ID 선택받기
     * @throws IllegalStateException 존재하지 않는 일정 ID 입력 시
     */
    @Transactional
    public void delete(Long todoId) {
        boolean exists = todoRepository.existsById(todoId);
        if (!exists) { throw new IllegalStateException("존재하지 않는 일정 ID입니다."); }
        todoRepository.deleteById(todoId);
    }
}
