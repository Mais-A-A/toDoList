package com.example.todolist.controller;

import com.example.todolist.entity.Todo;
import com.example.todolist.entity.User;
import com.example.todolist.service.ToDoService;
import com.example.todolist.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    private final ToDoService toDoService;
    private final UserService userService;

    public ToDoController(ToDoService toDoService, UserService userService) {
        this.toDoService = toDoService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Todo> createToDo(@RequestParam String title, @RequestParam String description, @RequestParam Long userId) {
        User user = userService.findByEmail("dummy@example.com").orElseThrow(); // Example user fetch logic
        Todo toDoItem = toDoService.createToDo(title, description, user);
        return ResponseEntity.ok(toDoItem);
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getUserTodos(@RequestParam Long userId) {
        List<Todo> todos = toDoService.getUserTodos(userId);
        return ResponseEntity.ok(todos);
    }
}

