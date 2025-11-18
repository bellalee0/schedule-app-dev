package com.example.scheduleappdev.todo.service;

import com.example.scheduleappdev.comment.dto.GetCommentForTodoResponse;
import com.example.scheduleappdev.comment.repository.CommentRepository;
import com.example.scheduleappdev.global.Exception.ErrorMessage;
import com.example.scheduleappdev.global.Exception.TodoServiceException;
import com.example.scheduleappdev.todo.dto.*;
import com.example.scheduleappdev.todo.entity.Todo;
import com.example.scheduleappdev.todo.repository.TodoRepository;
import com.example.scheduleappdev.user.entity.User;
import com.example.scheduleappdev.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    /**
     * 일정 생성하기
     *
     * @param request 내용 받기(일정 제목, 내용)
     * @param userId Http Session을 통해 유저ID 받기
     * @return 생성된 내용 DTO에 담아 반환
     * @throws TodoServiceException 존재하지 않는 유저로 접근 시 Not_Found_User 예외 발생
     */
    @Transactional
    public CreateTodoResponse create(CreateTodoRequest request, Long userId) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_USER));
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
     * @param page 페이지 번호 받기
     * @param size 페이지 당 항목 수 받기
     * @return 저장된 일정 DTO에 담아 List로 반환
     */
    @Transactional(readOnly = true)
    public Page<GetTodoResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());
        Page<Todo> todos = todoRepository.findAll(pageable);
        return todos.map(todo -> new GetTodoResponse(
                    todo.getId(), todo.getTitle(), todo.getContents(), commentRepository.countByTodoId(todo.getId()), todo.getCreator().getUsername(), todo.getCreatedAt(), todo.getModifiedAt()
                ));
    }

    /**
     * 선택 일정 조회하기
     *
     * @param todoId 일정 ID 받기
     * @return 조회된 일정 DTO에 담아 반환
     * @throws TodoServiceException 존재하지 않는 일정ID 입력 시 Not_Found_Todo 예외 발생
     */
    @Transactional(readOnly = true)
    public GetOneTodoResponse getOne(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_TODO));
        List<GetCommentForTodoResponse> comments = commentRepository.findByTodoId(todoId).stream()
                .map(comment -> new GetCommentForTodoResponse(
                        comment.getId(), comment.getComment(), comment.getCreator().getUsername(), comment.getCreatedAt(), comment.getModifiedAt()
                ))
                .sorted(Comparator.comparing(GetCommentForTodoResponse::getModifiedAt).reversed())
                .toList();
        return new GetOneTodoResponse(
                todo.getId(), todo.getTitle(), todo.getContents(), todo.getCreator().getUsername(), todo.getCreatedAt(), todo.getModifiedAt(), comments
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
     * @throws TodoServiceException 존재하지 않는 일정ID 입력 시 Not_Found_Todo 예외 발생
     * @throws TodoServiceException 로그인된 유저ID와 수정하려는 일정의 유저ID 불일치 시 Unmatched_User 예외 발생
     */
    @Transactional
    public UpdateTodoResponse update(Long todoId, UpdateTodoRequest request, Long userId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_TODO));
        if (!todo.getCreator().getId().equals(userId)) { throw new TodoServiceException(ErrorMessage.UNMATCHED_USER); }
        todo.update(request.getTitle());
        todoRepository.saveAndFlush(todo);
        return new UpdateTodoResponse(
                todo.getId(), todo.getTitle(), todo.getContents(), todo.getCreator().getUsername(), todo.getCreatedAt(), todo.getModifiedAt()
        );
    }

    /**
     * 선택 일정 삭제하기
     *
     * @param todoId API Path로 일정 ID 선택받기
     * @param userId Http Session을 통해 유저ID 받기
     * @throws TodoServiceException 존재하지 않는 일정ID 입력 시 Not_Found_Todo 예외 발생
     * @throws TodoServiceException 로그인된 유저ID와 삭제하려는 일정의 유저ID 불일치 시 Unmatched_User 예외 발생
     */
    @Transactional
    public void delete(Long todoId, Long userId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_TODO));
        if (!todo.getCreator().getId().equals(userId)) { throw new TodoServiceException(ErrorMessage.UNMATCHED_USER); }
        commentRepository.deleteByTodoId(todoId);
        todoRepository.deleteById(todoId);
    }
}
