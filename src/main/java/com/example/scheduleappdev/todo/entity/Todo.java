package com.example.scheduleappdev.todo.entity;

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

    @Column(nullable = false)
    private String creator;

    public Todo(String contents, String title, String creator) {
        this.contents = contents;
        this.title = title;
        this.creator = creator;
    }

    /**
     * 생성된 일정 수정용 메서드 (제목, 작성자명 수정)
     */
    public void update(String title, String creator) {
        this.title = title;
        this.creator = creator;
    }
}