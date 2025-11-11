package com.example.scheduleappdev.user.repository;

import com.example.scheduleappdev.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String user);
}
