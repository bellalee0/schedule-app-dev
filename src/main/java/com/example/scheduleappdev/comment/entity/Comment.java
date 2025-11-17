package com.example.scheduleappdev.comment.entity;

import com.example.scheduleappdev.global.Entity.BaseEntity;
import com.example.scheduleappdev.todo.entity.Todo;
import com.example.scheduleappdev.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    public Comment(String comment, User user, Todo todo) {
        this.comment = comment;
        this.creator = user;
        this.todo = todo;
    }

    /**
     * 생성된 댓글 수정용 메서드 (댓글 수정)
     * @param comment
     */
    public void update(String comment) {
        this.comment = comment;
    }
}
