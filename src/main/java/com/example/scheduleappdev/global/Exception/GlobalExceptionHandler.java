package com.example.scheduleappdev.global.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     *  Exception 클래스 내 모든 예외 처리
     *
     * @param ex 예외 이름
     * @return 예외의 message와 BAD_REQUEST 상태코드 반환
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        log.error("예외 발생: " + ex + " - " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    /**
     * TodoServiceException(커스텀 예외) 처리
     *
     * @param ex TodoServiceException(커스텀 예외)
     * @return 예외의 HTTP 상태코드와 내용 DTO에 담아 반환
     */
    @ExceptionHandler(TodoServiceException.class)
    public final ResponseEntity<ErrorResponseDTO> handleTodoServiceException(TodoServiceException ex) {
        log.error("TodoService 커스텀 예외 발생: " + ex.getErrorMessage().name() + " - " + ex.getMessage());
        return ResponseEntity.status(ex.getErrorMessage().getStatus()).body(new ErrorResponseDTO(ex.getErrorMessage(), ex.getMessage()));
    }

    /**
     * Validation 활용 시 발생하는 MethodArgumentNotValidException 예외 처리
     *
     * @param ex MethodArgumentNotValidException
     * @return 예외의 첫번째 message와 BAD_REQUEST 상태코드 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getDefaultMessage())
                .orElse("입력 값이 올바르지 않습니다.");
        log.error("Validation 예외 발생: " + errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, errorMessage));
    }

    /**
     * 데이터 무결성 제약 조건을 위반했을 때 발생하는 DataIntegrityViolationException 예외 처리
     *
     * @param ex DataIntegrityViolationException
     * @return 클라이언트 안내를 위한 문구와 BAD_REQUEST 상태코드 반환(세부 사항은 로그로 기록)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("예외 발생: " + ex + " - " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."));
    }
}
