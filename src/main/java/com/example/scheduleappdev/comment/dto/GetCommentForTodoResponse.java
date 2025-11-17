package com.example.scheduleappdev.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 단건 일정 조회 시 댓글 응답 DTO (댓글id, 댓글, 작성자, 생성일, 수정일)
 */
@Getter
public class GetCommentForTodoResponse {
    private final Long id;
    private final String comment;
    private final String creator;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetCommentForTodoResponse(Long id, String comment, String creator, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.comment = comment;
        this.creator = creator;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
