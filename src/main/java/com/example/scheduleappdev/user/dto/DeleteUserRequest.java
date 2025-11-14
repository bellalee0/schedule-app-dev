package com.example.scheduleappdev.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 유저 삭제 요청 DTO (비밀번호)
 */
@Getter
public class DeleteUserRequest {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
