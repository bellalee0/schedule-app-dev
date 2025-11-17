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

import java.util.Comparator;
import java.util.List;

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

    /**
     * 전체 댓글 조회하기
     *
     * @return 저장된 댓글 DTO에 담아 List로 반환
     */
    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(comment -> new GetCommentResponse(
                        comment.getTodo().getId(), comment.getId(), comment.getComment(), comment.getCreator().getUsername(), comment.getCreatedAt(), comment.getModifiedAt()
                ))
                .sorted(Comparator.comparing(GetCommentResponse::getTodoId)
                        .thenComparing(GetCommentResponse::getModifiedAt, Comparator.reverseOrder()))
                .toList();
    }

    /**
     * 선택 댓글 조회하기
     *
     * @param commentId 댓글 ID 받기
     * @return 조회된 댓글 DTO에 담아 반환
     * @throws TodoServiceException 존재하지 않는 댓글ID 입력 시 Not_Found_Comment 예외 발생
     */
    @Transactional(readOnly = true)
    public GetCommentResponse getOne(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_COMMENT));
        return new GetCommentResponse(
                comment.getTodo().getId(), comment.getId(), comment.getComment(), comment.getCreator().getUsername(), comment.getCreatedAt(), comment.getModifiedAt()
        );
    }
}
