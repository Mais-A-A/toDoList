package com.example.todolist.controller;

import com.example.todolist.dtos.UserCreateDTO;
import com.example.todolist.dtos.UserLoginDTO;
import com.example.todolist.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    public UserController(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserCreateDTO user){
        System.out.println(user.getUsername()+" "+user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String responseMessage = authService.createUser(user);
        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser( @RequestBody UserLoginDTO user){
        System.out.println(user.getUserName()+" "+user.getPassword());
        Map<?, ?> response = authService.loginByUserName(user);
        return ResponseEntity.ok(response);
    }
}
