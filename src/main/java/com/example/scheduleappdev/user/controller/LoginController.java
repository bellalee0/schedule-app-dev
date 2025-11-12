package com.example.scheduleappdev.user.controller;

import com.example.scheduleappdev.user.dto.*;
import com.example.scheduleappdev.user.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    /**
     * 회원가입(유저 생성하기)
     *
     * @param request HTTP Body로 내용 받기
     * @return 201 CREATED 상태코드와 생성된 내용 반환
     */
    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        CreateUserResponse result = loginService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 로그인
     *
     * @param request HTTP Body로 내용 받기(이메일, 비밀번호)
     * @param session HttpSession 주입
     * @return 200 OK 상태코드 반환
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request, HttpSession session) {
        UserForHttpSession user = loginService.login(request);
        session.setAttribute("loginUser", user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 로그아웃
     *
     * @param user loginUser 이름을 가진 세션 속성 찾아 DTO에 주입
     * @return 204 NO_CONTENT 상태코드 반환
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name="loginUser", required=false) UserForHttpSession user,
            HttpSession session) {
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
