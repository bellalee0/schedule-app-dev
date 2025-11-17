package com.example.scheduleappdev.comment.repository;

import com.example.scheduleappdev.comment.dto.GetCommentForTodoResponse;
import com.example.scheduleappdev.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * 일정 ID 기준으로 댓글 조회
     */
    List<Comment> findByTodoId(Long todoId);
}
