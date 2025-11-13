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
    NOT_LOGGED_IN(HttpStatus.UNAUTHORIZED, "로그인 되지 않았습니다."),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."),
    UNMATCHED_USER(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_FOUND_TODO(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
