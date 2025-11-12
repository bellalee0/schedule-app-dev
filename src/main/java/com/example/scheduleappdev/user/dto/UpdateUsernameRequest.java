package com.example.scheduleappdev.user.dto;

import lombok.Getter;

/**
 * 유저 수정 요청 DTO (유저명)
 */
@Getter
public class UpdateUsernameRequest {
    private String username;
}
