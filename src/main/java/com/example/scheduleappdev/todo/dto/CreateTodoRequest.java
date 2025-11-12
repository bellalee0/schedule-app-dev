package com.example.scheduleappdev.todo.dto;

import lombok.Getter;

/**
 * 일정 생성 요청 DTO (제목, 내용)
 */
@Getter
public class CreateTodoRequest {
    private String title;
    private String contents;
}
