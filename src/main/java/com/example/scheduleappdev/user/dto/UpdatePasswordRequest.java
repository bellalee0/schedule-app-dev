package com.example.scheduleappdev.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 유저 수정 요청 DTO (패스워드)
 */
@Getter
public class UpdatePasswordRequest {
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPassword;
    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    private String newPassword;
}