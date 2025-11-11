package com.example.scheduleappdev.todo.entity;

import com.example.scheduleappdev.global.Entity.BaseEntity;
import com.example.scheduleappdev.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "todos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    public Todo(String title, String contents, User creator) {
        this.title = title;
        this.contents = contents;
        this.creator = creator;
    }

    /**
     * 생성된 일정 수정용 메서드 (제목, 작성자명 수정)
     */
    public void update(String title, User creator) {
        this.title = title;
        this.creator = creator;
    }
}