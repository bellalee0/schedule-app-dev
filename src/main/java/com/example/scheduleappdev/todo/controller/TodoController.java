package com.example.scheduleappdev.todo.controller;

import com.example.scheduleappdev.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
}
