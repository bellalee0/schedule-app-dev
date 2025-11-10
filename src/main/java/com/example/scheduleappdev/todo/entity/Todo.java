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
    private String creator;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;

    public Todo(String creator, String title, String contents) {
        this.creator = creator;
        this.title = title;
        this.contents = contents;
    }
}