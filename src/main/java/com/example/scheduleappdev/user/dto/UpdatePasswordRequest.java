package com.example.scheduleappdev.user.dto;

import lombok.Getter;

/**
 * 유저 수정 요청 DTO (패스워드)
 */
@Getter
public class UpdatePasswordRequest {
    private String currentPassword;
    private String newPassword;
}