package com.example.scheduleappdev.user.service;

import com.example.scheduleappdev.config.PasswordEncoder;
import com.example.scheduleappdev.global.Exception.ErrorMessage;
import com.example.scheduleappdev.global.Exception.TodoServiceException;
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
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인
     *
     * @apiNote 1. 해당 이메일의 유저 찾기 / 2. 비밀번호 확인
     * @param request 내용 받기
     * @return 유저의 ID, 이메일, 유저명 DTO에 담아 전달
     * @throws TodoServiceException 존재하지 않는 이메일 입력 시 Not_Found_User 예외 발생
     * @throws TodoServiceException 비밀번호 불일치 시 Incorrect_Password 예외 발생
     */
    @Transactional
    public UserForHttpSession login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_USER));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) { throw new TodoServiceException(ErrorMessage.INCORRECT_PASSWORD); }
        return new UserForHttpSession(
                user.getId()
        );
    }
}
