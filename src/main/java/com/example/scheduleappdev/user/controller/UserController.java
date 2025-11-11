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
     * 유저 생성하기
     *
     * @param request HTTP Body로 내용 받기
     * @return 201 CREATED 상태코드와 생성된 내용 반환
     */
    @PostMapping("/users")
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
}
