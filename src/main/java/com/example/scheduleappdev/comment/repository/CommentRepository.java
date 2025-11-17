package com.example.scheduleappdev.comment.repository;

import com.example.scheduleappdev.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
