package com.example.scheduleappdev.global.Exception;

import org.springframework.http.HttpStatus;

/**
 *  비밀번호 불일치 시 발생하는 예외 클래스
 */
public class IncorrectPassword extends TodoServiceException {
    public IncorrectPassword(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
