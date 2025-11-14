package com.example.scheduleappdev.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 일정 생성 요청 DTO (제목, 내용)
 */
@Getter
public class CreateTodoRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 30, message = "30자 이내의 제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 100, message = "최대 100자까지 입력 가능합니다.")
    private String contents;
}
