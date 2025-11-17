package com.example.scheduleappdev.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 댓글 조회 응답 DTO (일정id, 댓글id, 댓글, 작성자, 생성일, 수정일)
 */
@Getter
public class GetCommentResponse {
    private final Long todoId;
    private final Long id;
    private final String comment;
    private final String creator;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetCommentResponse(Long todoId, Long id, String comment, String creator, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.todoId = todoId;
        this.id = id;
        this.comment = comment;
        this.creator = creator;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
