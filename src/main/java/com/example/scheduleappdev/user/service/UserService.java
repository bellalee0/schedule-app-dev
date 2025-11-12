package com.example.scheduleappdev.user.service;

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

    /**
     * 회원가입(유저 생성하기)
     *
     * @param request 내용 받기
     * @return 생성된 내용 DTO에 담아 반환
     */
    @Transactional
    public CreateUserResponse create(CreateUserRequest request) {
        User user = new User(
                request.getUsername(), request.getEmail(), request.getPassword()
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
     * @throws IllegalStateException 존재하지 않는 유저 ID 입력 시
     */
    @Transactional(readOnly = true)
    public GetUserResponse getOne(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 ID입니다."));
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
     * @throws IllegalStateException 존재하지 않는 유저 ID 입력 시
     */
    @Transactional
    public UpdateUserResponse updateUsername(Long userId, UpdateUsernameRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저 ID입니다."));
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
     * @throws IllegalStateException 존재하지 않는 유저 ID 입력 시
     * @throws IllegalStateException 현재 비밀번호 불일치 시
     */
    @Transactional
    public UpdateUserResponse updatePassword(Long userId, UpdatePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저 ID입니다."));
        if(!user.getPassword().equals(request.getCurrentPassword())) { throw new IllegalStateException("현재 비밀번호가 일치하지 않습니다."); }
        user.updatePassword(request.getNewPassword());
        userRepository.saveAndFlush(user);
        return new UpdateUserResponse(
                user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getModifiedAt()
        );
    }

    /**
     * 선택 유저 삭제하기
     *
     * @param userId API Path로 유저 ID 선택받기
     * @throws IllegalStateException 존재하지 않는 유저 ID 입력 시
     */
    @Transactional
    public void delete(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) { throw new IllegalStateException("존재하지 않는 유저 ID입니다."); }
        userRepository.deleteById(userId);
    }
}
