package com.example.scheduleappdev.comment.repository;

import com.example.scheduleappdev.comment.entity.Comment;
import com.example.scheduleappdev.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * 일정 ID 기준으로 댓글 조회
     */
    List<Comment> findByTodoId(Long todoId);

    /**
     * 일정 ID 기준으로 댓글 삭제
     */
    void deleteByTodoId(Long todoId);

    /**
     * user 기준으로 댓글 조회
     */
    List<Comment> findByCreator(User user);

    /**
     * user 기준으로 댓글 삭제
     */
    void deleteByCreator(User user);
}
