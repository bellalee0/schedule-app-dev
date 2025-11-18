package com.example.scheduleappdev.user.dto;

import com.example.scheduleappdev.comment.dto.GetCommentResponse;
import com.example.scheduleappdev.todo.dto.GetTodoResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 단건 유저 조회 응답 DTO (유저ID, 유저명, 이메일, 생성일, 수정일, 유저가 작성한 일정, 유저가 작성한 댓글)
 */
@Getter
public class GetOneUserResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<GetTodoResponse> todos;
    private final List<GetCommentResponse> comments;

    public GetOneUserResponse(Long id, String username, String email, LocalDateTime createdAt, LocalDateTime modifiedAt, List<GetTodoResponse> todos, List<GetCommentResponse> comments) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.todos = todos;
        this.comments = comments;
    }
}
