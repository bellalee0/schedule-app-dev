package com.example.scheduleappdev.comment.controller;

import com.example.scheduleappdev.comment.dto.*;
import com.example.scheduleappdev.comment.service.CommentService;
import com.example.scheduleappdev.global.Exception.TodoServiceException;
import com.example.scheduleappdev.user.dto.UserForHttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        CreateCommentResponse result = commentService.create(todoId, request, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 전체 댓글 조회하기
     *
     * @param page 쿼리 파라미터로 페이지 번호 받기
     * @param size 쿼리 파라미터로 페이지 당 항목 수 받기
     * @return 200 OK 상태코드와 조회된 내용 반환
     */
    @GetMapping("/todos/comments")
    public ResponseEntity<Page<GetCommentResponse>> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<GetCommentResponse> result = commentService.getAll(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 댓글 조회하기
     *
     * @param commentId API Path로 댓글 ID 선택받기
     * @return 200 OK 상태코드와 조회된 내용 반환
     */
    @GetMapping("/todos/comments/{commentId}")
    public ResponseEntity<GetCommentResponse> getOneComment(@PathVariable Long commentId) {
        GetCommentResponse result = commentService.getOne(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 댓글 수정하기
     *
     * @param sessionUser loginUser 이름을 가진 세션 속성 찾아 DTO에 주입
     * @param commentId API Path로 댓글 ID 선택받기
     * @param request HTTP Body로 내용 받기
     * @return 200 OK 상태코드와 수정된 내용 반환
     * @throws TodoServiceException 비로그인 상태로 접근 시 Not_Logged_In 예외 발생
     */
    @PutMapping("/todos/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @SessionAttribute(name = "loginUser", required = false) UserForHttpSession sessionUser,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request) {
        UpdateCommentResponse result = commentService.update(commentId, request, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 댓글 삭제하기
     *
     * @param sessionUser loginUser 이름을 가진 세션 속성 찾아 DTO에 주입
     * @param commentId API Path로 댓글 ID 선택받기
     * @return 204 NO_CONTENT 상태코드 반환
     * @throws TodoServiceException 비로그인 상태로 접근 시 Not_Logged_In 예외 발생
     */
    @DeleteMapping("/todos/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name = "loginUser", required = false) UserForHttpSession sessionUser,
            @PathVariable Long commentId) {
        commentService.delete(commentId, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
