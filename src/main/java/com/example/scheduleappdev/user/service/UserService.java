package com.example.scheduleappdev.user.service;

import com.example.scheduleappdev.global.config.PasswordEncoder;
import com.example.scheduleappdev.global.Exception.ErrorMessage;
import com.example.scheduleappdev.global.Exception.TodoServiceException;
import com.example.scheduleappdev.user.dto.*;
import com.example.scheduleappdev.user.entity.User;
import com.example.scheduleappdev.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입(유저 생성하기)
     *
     * @param request 내용 받기
     * @return 생성된 내용 DTO에 담아 반환
     */
    @Transactional
    public CreateUserResponse create(CreateUserRequest request) {
        User user = new User(
                request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword())
        );
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getCreatedAt(), savedUser.getModifiedAt()
        );
    }

    /**
     * 전체 유저 조회하기
     *
     * @return 저장된 유저 DTO에 담아 List로 반환
     */
    @Transactional(readOnly = true)
    public List<GetUserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new GetUserResponse(
                        user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getModifiedAt()
                ))
                .toList();
    }

    /**
     * 선택 유저 조회하기
     *
     * @param userId 유저 ID 받기
     * @return 조회된 유저 DTO에 담아 반환
     * @throws TodoServiceException 존재하지 않는 유저 ID 입력 시 Not_Found_User 예외 발생
     */
    @Transactional(readOnly = true)
    public GetUserResponse getOne(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_USER));
        return new GetUserResponse(
                user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getModifiedAt()
        );
    }

    /**
     * 선택 유저 유저명 수정하기
     *
     * @param userId 유저 ID 받기
     * @param request 수정할 내용 받기(유저명)
     * @return 수정된 내용 DTO에 담아 반환
     * @throws TodoServiceException 존재하지 않는 유저 ID 입력 시 Not_Found_User 예외 발생
     */
    @Transactional
    public UpdateUserResponse updateUsername(Long userId, UpdateUsernameRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_USER));
        user.updateUsername(request.getUsername());
        userRepository.saveAndFlush(user);
        return new UpdateUserResponse(
                user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getModifiedAt()
        );
    }

    /**
     * 선택 유저 비밀번호 수정하기
     *
     * @param userId 유저 ID 받기
     * @param request 수정할 내용 받기(현재 비밀번호, 새로운 비밀번호)
     * @return 수정한 유저 정보 DTO에 담아 반환
     * @throws TodoServiceException 존재하지 않는 유저 ID 입력 시 Not_Found_User 예외 발생
     * @throws TodoServiceException 현재 비밀번호 불일치 시 Incorrect_Password 예외 발생
     */
    @Transactional
    public UpdateUserResponse updatePassword(Long userId, UpdatePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_USER));
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) { throw new TodoServiceException(ErrorMessage.INCORRECT_PASSWORD); }
        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.saveAndFlush(user);
        return new UpdateUserResponse(
                user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getModifiedAt()
        );
    }

    /**
     * 선택 유저 삭제하기
     *
     * @param userId API Path로 유저 ID 선택받기
     * @throws TodoServiceException 존재하지 않는 유저 ID 입력 시 Not_Found_User 예외 발생
     * @throws TodoServiceException 비밀번호 불일치 시 Incorrect_Password 예외 발생
     */
    @Transactional
    public void delete(Long userId, DeleteUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TodoServiceException(ErrorMessage.NOT_FOUND_USER));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) { throw new TodoServiceException(ErrorMessage.INCORRECT_PASSWORD); }
        userRepository.deleteById(userId);
    }
}
