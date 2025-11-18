package com.example.scheduleappdev.todo.repository;

import com.example.scheduleappdev.todo.dto.GetTodoResponse;
import com.example.scheduleappdev.todo.entity.Todo;
import com.example.scheduleappdev.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    /**
     * user 기준으로 일정 조회
     */
    List<Todo> findByCreator(User user);

    /**
     * user 기준으로 일정 삭제
     */
    void deleteByCreator(User user);
}
