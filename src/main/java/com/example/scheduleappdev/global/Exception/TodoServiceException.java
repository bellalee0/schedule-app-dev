package com.example.scheduleappdev.global.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 커스텀 에러 전체를 관리하는 예외 클래스
 */
@Getter
public class TodoServiceException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public TodoServiceException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
