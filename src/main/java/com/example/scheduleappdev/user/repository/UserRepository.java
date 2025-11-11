package com.example.scheduleappdev.user.repository;

import com.example.scheduleappdev.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 유저명으로 유저 검색
     *
     * @param user 유저명 입력
     * @return Optional<User>로 검색 결과 반환
     */
    Optional<User> findByUsername(String user);
}
