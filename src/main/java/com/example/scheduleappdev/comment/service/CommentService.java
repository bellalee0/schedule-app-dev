package com.example.scheduleappdev.comment.service;

import com.example.scheduleappdev.comment.dto.*;
import com.example.scheduleappdev.comment.entity.Comment;
import com.example.scheduleappdev.comment.repository.CommentRepository;
import com.example.scheduleappdev.global.Exception.ErrorMessage;
import com.example.scheduleappdev.global.Exception.TodoServiceException;
import com.example.scheduleappdev.todo.entity.Todo;
import com.example.scheduleappdev.todo.repository.TodoRepository;
import com.example.scheduleappdev.user.entity.User;
import com.example.scheduleappdev.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    /**
     * 댓글 생성하기
     *
     * @param todoId 일정 ID 받기
     * @param request 내용 받기(댓글)
     * @param userId Http Session을 통해 유저ID 받기
     * @return 생성된 내용 DTO에 담아 반환
     * @throws TodoServiceException 존재하지 않는 유저로 접근 시 Not_Found_User 예외 발생
     * @throws TodoServiceException 존재하지 않는 일정ID 입력 시 Not_Found_Todo 예외 발생
     */
    @Transactional
    public CreateCommentResponse create(Long todoId, CreateCommentRequest request, Long userId) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_USER));
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_TODO));
        Comment comment = new Comment(
                request.getComment(), creator, todo
        );
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getTodo().getId(), savedComment.getId(), savedComment.getComment(), savedComment.getCreator().getUsername(), savedComment.getCreatedAt(), savedComment.getModifiedAt()
        );
    }
}
