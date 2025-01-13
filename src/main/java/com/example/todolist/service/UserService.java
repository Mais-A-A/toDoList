package com.example.todolist.service;

import com.example.todolist.config.JwtService;
import com.example.todolist.entity.User;
import com.example.todolist.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final JwtService jwtService;

    private final UserRepo userRepo;

    public UserService(JwtService jwtService, UserRepo userRepo) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    public String getUsernameFromToken(String token){
        token = token.split(" ")[1];
        return jwtService.extractUsername(token);
    }

    public Optional<User> getUserByUsername(String username){
        return userRepo.findByUsername(username);
    }
}
