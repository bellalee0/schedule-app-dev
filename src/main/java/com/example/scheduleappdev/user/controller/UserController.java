package com.example.scheduleappdev.user.controller;

import com.example.scheduleappdev.user.dto.*;
import com.example.scheduleappdev.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
