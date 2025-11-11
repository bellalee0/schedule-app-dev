package com.example.scheduleappdev.user.dto;

import lombok.Getter;

/**
 * 유저 생성 요청 DTO (유저명, 이메일)
 */
@Getter
public class CreateUserRequest {
    private String username;
    private String email;
}
