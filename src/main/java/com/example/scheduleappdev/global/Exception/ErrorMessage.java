package com.example.scheduleappdev.global.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 예외 메시지 저장용 Enum
 */
@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
