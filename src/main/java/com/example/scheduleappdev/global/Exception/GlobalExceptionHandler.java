package com.example.scheduleappdev.global.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     *  Exception 클래스 내 모든 예외 처리
     *
     * @param ex 예외 이름
     * @return 예외의 message와 BAD_REQUEST 상태코드 반환
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", new Date());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", HttpStatus.BAD_REQUEST);
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * TodoServiceException을 상속받은 커스텀 예외 처리
     *
     * @param ex 예외 이름
     * @return 각 예외에 설정한 message와 HTTP 상태코드 반환
     */
    @ExceptionHandler(TodoServiceException.class)
    public final ResponseEntity<Map<String, Object>> handleTodoServiceException(TodoServiceException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", new Date());
        response.put("status", ex.getStatus().value());
        response.put("error", ex.getStatus());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, ex.getStatus());
    }
}
