package com.example.scheduleappdev.user.controller;

import com.example.scheduleappdev.global.Exception.ErrorMessage;
import com.example.scheduleappdev.global.Exception.TodoServiceException;
import com.example.scheduleappdev.user.dto.*;
import com.example.scheduleappdev.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 회원가입(유저 생성하기)
     *
     * @param request HTTP Body로 내용 받기
     * @return 201 CREATED 상태코드와 생성된 내용 반환
     */
    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> createUser( @Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse result = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 전체 유저 조회하기
     *
     * @param page 쿼리 파라미터로 페이지 번호 받기
     * @param size 쿼리 파라미터로 페이지 당 항목 수 받기
     * @return 200 OK 상태코드와 조회된 내용 반환
     */
    @GetMapping("/users")
    public ResponseEntity<Page<GetUserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<GetUserResponse> result = userService.getAll(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 유저 조회하기
     *
     * @param userId API Path로 유저 ID 선택받기
     * @return 200 OK 상태코드와 조회된 내용 반환
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetOneUserResponse> getOneUser(@PathVariable Long userId) {
        GetOneUserResponse result = userService.getOne(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 유저 유저명 수정하기
     *
     * @param sessionUser loginUser 이름을 가진 세션 속성 찾아 DTO에 주입
     * @param userId API Path로 유저 ID 선택받기
     * @param request HTTP Body로 내용 받기
     * @return 200 OK 상태코드와 수정된 내용 반환
     * @throws TodoServiceException 비로그인 상태로 접근 시 Not_Logged_In 예외 발생
     * @throws TodoServiceException 로그인된 유저ID와 수정하려는 유저ID 불일치 시 Unmatched_User 예외 발생
     */
    @PutMapping("/users/{userId}/username")
    public ResponseEntity<UpdateUserResponse> updateUsername(
            @SessionAttribute(name = "loginUser", required = false) UserForHttpSession sessionUser,
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUsernameRequest request) {
        if (sessionUser == null) { throw new TodoServiceException(ErrorMessage.NOT_LOGGED_IN); }
        // TODO : API Path로 유저ID 입력받지 말고, 처음부터 세션정보에서 ID 가져오도록 하면 어떨까??
        if (!sessionUser.getId().equals(userId)) { throw new TodoServiceException(ErrorMessage.UNMATCHED_USER); }
        UpdateUserResponse result = userService.updateUsername(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 유저 비밀번호 수정하기
     *
     * @param sessionUser loginUser 이름을 가진 세션 속성 찾아 DTO에 주입
     * @param userId API Path로 유저 ID 선택받기
     * @param request HTTP Body로 내용 받기
     * @return 200 OK 상태코드와 수정된 내용 반환
     * @throws TodoServiceException 비로그인 상태로 접근 시 Not_Logged_In 예외 발생
     * @throws TodoServiceException 로그인된 유저ID와 수정하려는 유저ID 불일치 시 Unmatched_User 예외 발생
     */
    @PutMapping("/users/{userId}/password")
    public ResponseEntity<UpdateUserResponse> updateUserPassword(
            @SessionAttribute(name = "loginUser", required = false) UserForHttpSession sessionUser,
            @PathVariable Long userId,
            @Valid @RequestBody UpdatePasswordRequest request
    ) {
        if (sessionUser == null) { throw new TodoServiceException(ErrorMessage.NOT_LOGGED_IN); }
        // TODO : 세션정보에서 ID 가져오기 22
        if (!sessionUser.getId().equals(userId)) { throw new TodoServiceException(ErrorMessage.UNMATCHED_USER); }
        UpdateUserResponse result = userService.updatePassword(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 유저 삭제하기
     *
     * @param sessionUser loginUser 이름을 가진 세션 속성 찾아 DTO에 주입
     * @param userId API Path로 유저 ID 선택받기
     * @param request HTTP Body로 내용 받기
     * @return 204 NO_CONTENT 상태코드 반환
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(
            @SessionAttribute(name = "loginUser", required = false) UserForHttpSession sessionUser,
            HttpSession session,
            @PathVariable Long userId,
            @Valid @RequestBody DeleteUserRequest request) {
        if (userId.equals(sessionUser.getId())) { session.invalidate(); }
        userService.delete(userId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
