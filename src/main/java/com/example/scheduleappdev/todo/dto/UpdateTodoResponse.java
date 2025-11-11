package com.example.scheduleappdev.todo.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정 수정 응답 DTO (일정id, 제목, 내용, 작성자, 생성일, 수정일)
 */
@Getter
public class UpdateTodoResponse {
    private final Long id;
    private final String title;
    private final String contents;
    private final String creator;
    private final LocalDateTime creationAt;
    private final LocalDateTime modifiedAt;

    public UpdateTodoResponse(Long id, String title, String contents, String creator, LocalDateTime creationAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.creator = creator;
        this.creationAt = creationAt;
        this.modifiedAt = modifiedAt;
    }
}
