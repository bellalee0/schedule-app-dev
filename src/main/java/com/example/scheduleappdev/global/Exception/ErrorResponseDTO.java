package com.example.scheduleappdev.global.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * 예외 처리 DTO
 */
@Getter
@AllArgsConstructor
public class ErrorResponseDTO {
    private final LocalDateTime timestamp;
    private final int status;
    private final String code;
    private final String message;

    public ErrorResponseDTO(ErrorMessage errorCode, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = errorCode.getStatus().value();
        this.code = errorCode.name();
        this.message = message;
    }

    public ErrorResponseDTO(int status, HttpStatus code, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.code = code.name();
        this.message = message;
    }
}
