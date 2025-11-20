package com.example.scheduleappdev.user.entity;

import com.example.scheduleappdev.global.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * 생성된 유저 수정용 메서드 (유저명 수정)
     */
    public void updateUsername(String username) {
        this.username = username;
    }

    /**
     * 생성된 유저 수정용 메서드 (패스워드 수정)
     */
    public void updatePassword(String password) {
        this.password = password;
    }
}
