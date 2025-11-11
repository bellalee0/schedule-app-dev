package com.example.scheduleappdev.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 유저 수정 응답 DTO (유저ID, 유저명, 이메일, 생성일, 수정일)
 */
@Getter
public class UpdateUserResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UpdateUserResponse(Long id, String username, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
