package com.example.scheduleappdev.todo.dto;

import com.example.scheduleappdev.comment.dto.GetCommentForTodoResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 단건 일정 조회 응답 DTO (일정id, 제목, 내용, 작성자, 생성일, 수정일, 댓글 List)
 */
@Getter
public class GetOneTodoResponse {
    private final Long id;
    private final String title;
    private final String contents;
    private final String creator;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<GetCommentForTodoResponse> comments;

    public GetOneTodoResponse(Long id, String title, String contents, String creator, LocalDateTime createdAt, LocalDateTime modifiedAt, List<GetCommentForTodoResponse> comments) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.creator = creator;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }
}
