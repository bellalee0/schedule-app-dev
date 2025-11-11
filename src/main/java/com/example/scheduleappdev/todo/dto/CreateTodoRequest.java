package com.example.scheduleappdev.todo.dto;

import lombok.Getter;

/**
 * 일정 생성 요청 DTO (제목, 내용, 작성자)
 */
@Getter
public class CreateTodoRequest {
    private String title;
    private String contents;
    private String creator;
}
