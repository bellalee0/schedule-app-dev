package com.example.scheduleappdev.user.service;

import com.example.scheduleappdev.user.dto.CreateUserRequest;
import com.example.scheduleappdev.user.dto.CreateUserResponse;
import com.example.scheduleappdev.user.dto.GetUserResponse;
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
     * 유저 생성하기
     *
     * @param request 내용 받기
     * @return 생성된 내용 DTO에 담아 반환
     */
    @Transactional
    public CreateUserResponse create(CreateUserRequest request) {
        User user = new User(
                request.getUsername(), request.getEmail()
        );
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getCreatedAt(), savedUser.getModifiedAt()
        );
    }

    /**
     * 전체 유저 조회하기
     *
     * @return 저장된 일정 DTO에 담아 List로 반환
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
}
