package com.example.scheduleappdev.user.service;

import com.example.scheduleappdev.global.Exception.IncorrectPassword;
import com.example.scheduleappdev.user.dto.*;
import com.example.scheduleappdev.user.entity.User;
import com.example.scheduleappdev.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    /**
     * 로그인
     *
     * @apiNote 1. 해당 이메일의 유저 찾기 / 2. 비밀번호 확인
     * @param request 내용 받기
     * @return 유저의 ID, 이메일, 유저명 DTO에 담아 전달
     * @throws IllegalArgumentException 존재하지 않는 이메일 입력 시
     * @throws IncorrectPassword 비밀번호 불일치 시
     */
    @Transactional
    public UserForHttpSession login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        if (!user.getPassword().equals(request.getPassword())) { throw new IncorrectPassword("비밀번호가 일치하지 않습니다."); }
        return new UserForHttpSession(
                user.getId(), user.getEmail(), user.getUsername()
        );
    }
}
