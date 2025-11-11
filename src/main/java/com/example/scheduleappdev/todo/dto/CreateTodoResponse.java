package com.example.scheduleappdev.todo.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정 생성 응답 DTO (일정id, 제목, 내용, 작성자, 생성일, 수정일)
 */
@Getter
public class CreateTodoResponse {
    private final Long id;
    private final String title;
    private final String contents;
    private final String creator;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CreateTodoResponse(Long id, String title, String contents, String creator, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.creator = creator;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
