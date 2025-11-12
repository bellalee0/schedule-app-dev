package com.example.scheduleappdev.todo.dto;

import lombok.Getter;

/**
 * 일정 수정 요청 DTO (제목)
 */
@Getter
public class UpdateTodoRequest {
    private String title;
}
