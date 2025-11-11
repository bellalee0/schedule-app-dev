package com.example.scheduleappdev.user.dto;

import lombok.Getter;

/**
 * 로그인 요청 DTO (이메일, 비밀번호)
 */
@Getter
public class LoginRequest {
    private String email;
    private String password;
}
