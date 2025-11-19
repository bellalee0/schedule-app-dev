package com.example.scheduleappdev.comment.repository;

import com.example.scheduleappdev.comment.entity.Comment;
import com.example.scheduleappdev.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 페이징 적용하여 전체 댓글 조회
     */
    Page<Comment> findAll(Pageable pageable);

    /**
     * 페이징 적용하여 일정 ID 기준으로 댓글 조회
     */
    Page<Comment> findByTodoId(Long todoId, Pageable pageable);

    /**
     * 일정 ID 기준으로 댓글 삭제
     */
    void deleteByTodoId(Long todoId);

    /**
     * 일정 ID 기준으로 댓글 수 count
     */
    int countByTodoId(Long todoId);

    /**
     * user 기준으로 댓글 조회
     */
    Page<Comment> findByCreator(User user, Pageable pageable);

    /**
     * user 기준으로 댓글 삭제
     */
    void deleteByCreator(User user);
}
