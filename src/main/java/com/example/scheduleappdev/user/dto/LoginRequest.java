package com.example.scheduleappdev.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 로그인 요청 DTO (이메일, 비밀번호)
 */
@Getter
public class LoginRequest {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "'id@domain.dom'의 형식으로 입력해주세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
