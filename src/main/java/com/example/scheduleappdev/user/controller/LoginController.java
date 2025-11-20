package com.example.scheduleappdev.user.controller;

import com.example.scheduleappdev.global.Exception.TodoServiceException;
import com.example.scheduleappdev.user.dto.*;
import com.example.scheduleappdev.user.service.LoginService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
     * 로그인
     *
     * @param request HTTP Body로 내용 받기(이메일, 비밀번호)
     * @param session HttpSession 주입
     * @return 200 OK 상태코드 반환
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest request, HttpSession session) {
        UserForHttpSession user = loginService.login(request);
        session.setAttribute("loginUser", user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 로그아웃
     *
     * @param user loginUser 이름을 가진 세션 속성 찾아 DTO에 주입
     * @return 204 NO_CONTENT 상태코드 반환
     * @throws TodoServiceException 비로그인 상태로 접근 시 Not_Logged_In 예외 발생
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name="loginUser", required=false) UserForHttpSession user,
            HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
