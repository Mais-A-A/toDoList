package com.example.todolist.service;

import com.example.todolist.entity.User;
import com.example.todolist.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;

    //@Autowired
//    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        String pass = user.getPassword();
        user.setPassword(pass);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

