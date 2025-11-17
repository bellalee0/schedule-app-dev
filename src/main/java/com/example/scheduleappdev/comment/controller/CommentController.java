package com.example.scheduleappdev.comment.controller;

import com.example.scheduleappdev.comment.dto.*;
import com.example.scheduleappdev.comment.service.CommentService;
import com.example.scheduleappdev.global.Exception.ErrorMessage;
import com.example.scheduleappdev.global.Exception.TodoServiceException;
import com.example.scheduleappdev.user.dto.UserForHttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 생성하기
     *
     * @param sessionUser loginUser 이름을 가진 세션 속성 찾아 DTO에 주입
     * @param todoId API Path로 일정 ID 선택받기
     * @param request HTTP Body로 내용 받기
     * @return 201 CREATED 상태코드와 생성된 내용 반환
     * @throws TodoServiceException 비로그인 상태로 접근 시 Not_Logged_In 예외 발생
     */
    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @SessionAttribute(name = "loginUser", required = false) UserForHttpSession sessionUser,
            @PathVariable Long todoId,
            @Valid @RequestBody CreateCommentRequest request) {
        if (sessionUser == null) { throw new TodoServiceException(ErrorMessage.NOT_LOGGED_IN); }
        CreateCommentResponse result = commentService.create(todoId, request, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 전체 일정 조회하기
     *
     * @return 200 OK 상태코드와 조회된 내용 반환
     */
    @GetMapping("/todos/comments")
    public ResponseEntity<List<GetCommentResponse>> getAllComments() {
        List<GetCommentResponse> result = commentService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
