package com.example.scheduleappdev.user.dto;

import lombok.Getter;

/**
 * 세션 저장 DTO (유저ID)
 */
@Getter
public class UserForHttpSession {
    private Long id;

    public UserForHttpSession(Long id) {
        this.id = id;
    }
}
