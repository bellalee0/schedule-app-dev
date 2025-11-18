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
import jakarta.persistence.criteria.Order;
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
     * @param page 페이지 번호 받기
     * @param size 페이지 당 항목 수 받기
     * @return 저장된 댓글 DTO에 담아 List로 반환
     */
    @Transactional(readOnly = true)
    public Page<GetCommentResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("todoId").ascending().and(Sort.by("modifiedAt").descending()));
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(comment -> new GetCommentResponse(
                        comment.getTodo().getId(), comment.getId(), comment.getComment(), comment.getCreator().getUsername(), comment.getCreatedAt(), comment.getModifiedAt()
                ));
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

    /**
     * 선택 댓글 수정하기
     *
     * @apiNote 1. 댓글ID로 일정 찾기(없으면 예외 처리) / 2. 일정의 유저ID와 로그인된 유저의 ID 일치 여부 확인(불일치시 예외 처리) / 3. 댓글 수정
     * @param commentId 일정 ID 받기
     * @param request 수정할 내용 받기(댓글)
     * @param userId Http Session을 통해 유저ID 받기
     * @return 수정된 내용 DTO에 담아 반환
     * @throws TodoServiceException 존재하지 않는 댓글ID 입력 시 Not_Found_Comment 예외 발생
     * @throws TodoServiceException 로그인된 유저ID와 수정하려는 일정의 유저ID 불일치 시 Unmatched_User 예외 발생
     */
    @Transactional
    public UpdateCommentResponse update(Long commentId, UpdateCommentRequest request, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_COMMENT));
        if (!comment.getCreator().getId().equals(userId)) { throw new TodoServiceException(ErrorMessage.UNMATCHED_USER); }
        comment.update(request.getComment());
        commentRepository.saveAndFlush(comment);
        return new UpdateCommentResponse(
                comment.getTodo().getId(), comment.getId(), comment.getComment(), comment.getCreator().getUsername(), comment.getCreatedAt(), comment.getModifiedAt()
        );
    }

    /**
     * 선택 댓글 삭제하기
     *
     * @param commentId API Path로 댓글 ID 선택받기
     * @param userId Http Session을 통해 유저ID 받기
     * @throws TodoServiceException 존재하지 않는 댓글ID 입력 시 Not_Found_Comment 예외 발생
     * @throws TodoServiceException 로그인된 유저ID와 삭제하려는 댓글의 유저ID 불일치 시 Unmatched_User 예외 발생
     */
    @Transactional
    public void delete(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_COMMENT));
        if (!comment.getCreator().getId().equals(userId)) { throw new TodoServiceException(ErrorMessage.UNMATCHED_USER); }
        commentRepository.deleteById(commentId);
    }
}
