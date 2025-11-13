package com.example.scheduleappdev.user.dto;

import lombok.Getter;

/**
 * 유저 삭제 요청 DTO (비밀번호)
 */
@Getter
public class DeleteUserRequest {
    private String password;
}
