package com.example.todolist.controller;


import com.example.todolist.entity.User;
import com.example.todolist.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String email, @RequestParam String password, @RequestParam String name) {
        System.out.println("hi from register");

        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use.");
        }
        User user = new User(email, password, name);
        //userService.register(email, password, name);
        userService.register(user);
        return ResponseEntity.ok("User registered successfully.");
    }
}