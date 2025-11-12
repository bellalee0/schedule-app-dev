package com.example.scheduleappdev.global.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 커스텀 에러 전체를 관리하는 부모 클래스
 */
@Getter
public class TodoServiceException extends RuntimeException {
    private final HttpStatus status;

    public TodoServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
