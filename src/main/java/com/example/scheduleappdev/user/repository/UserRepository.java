package com.example.scheduleappdev.user.repository;

import com.example.scheduleappdev.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 페이징 적용하여 전체 유저 조회
     */
    Page<User> findAll(Pageable pageable);

    /**
     * 이메일로 유저 검색
     *
     * @param email 이메일 입력
     * @return Optional<User>로 검색 결과 반환
     */
    Optional<User> findByEmail(String email);
}
