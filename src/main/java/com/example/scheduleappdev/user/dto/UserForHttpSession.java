package com.example.scheduleappdev.user.dto;

import lombok.Getter;

/**
 * 세션 저장 DTO (유저ID, 이메일, 유저명)
 */
@Getter
public class UserForHttpSession {
    private Long id;
    private String email;
    private String username;

    public UserForHttpSession(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
}
