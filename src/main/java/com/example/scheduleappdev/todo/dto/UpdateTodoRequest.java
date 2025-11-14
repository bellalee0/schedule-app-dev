package com.example.scheduleappdev.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 일정 수정 요청 DTO (제목)
 */
@Getter
public class UpdateTodoRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 30, message = "30자 이내의 제목을 입력해주세요.")
    private String title;
}
