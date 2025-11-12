package com.example.scheduleappdev.user.controller;

import com.example.scheduleappdev.user.dto.*;
import com.example.scheduleappdev.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        CreateUserResponse result = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 전체 유저 조회하기
     *
     * @return 200 OK 상태코드와 조회된 내용 반환
     */
    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAllUsers() {
        List<GetUserResponse> result = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 일정 조회하기
     *
     * @param userId API Path로 유저 ID 선택받기
     * @return 200 OK 상태코드와 조회된 내용 반환
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getOneUser(@PathVariable Long userId) {
        GetUserResponse result = userService.getOne(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 유저 유저명 수정하기
     *
     * @param sessionUser loginUser 이름을 가진 세션 속성 찾아 DTO에 주입
     * @param userId API Path로 유저 ID 선택받기
     * @param request HTTP Body로 내용 받기
     * @return 200 OK 상태코드와 수정된 내용 반환 / 비로그인 혹은 id 불일치시 401 UNAUTHORIZED 상태코드 반환
     */
    @PutMapping("/users/{userId}/username")
    public ResponseEntity<UpdateUserResponse> updateUsername(
            @SessionAttribute(name = "loginUser", required = false) UserForHttpSession sessionUser,
            @PathVariable Long userId, @RequestBody UpdateUsernameRequest request) {
        if (sessionUser == null || !sessionUser.getId().equals(userId)) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); }
        UpdateUserResponse result = userService.updateUsername(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 선택 유저 삭제하기
     *
     * @param userId API Path로 유저 ID 선택받기
     * @return 204 NO_CONTENT 상태코드 반환
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
