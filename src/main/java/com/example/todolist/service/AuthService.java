package com.example.todolist.service;

import com.example.todolist.dtos.UserCreateDTO;
import com.example.todolist.dtos.UserLoginDTO;
import com.example.todolist.config.JwtService;
import com.example.todolist.entity.User;
import com.example.todolist.repository.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class AuthService implements UserDetailsService {

    @Value("{jwt.secret}")
    private String JWT_SECRET;

    private JwtService jwtService;

    private UserRepo userRepo;

    private AuthenticationManager authenticationManager;

    public AuthService(@Lazy JwtService jwtService, UserRepo userRepo,
                       @Lazy AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
    }

    public Optional<User> findUserById(Long id) {
        return userRepo.findById(id);
    }

    public String createUser(UserCreateDTO user) {

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        System.out.println("hiiiiiiiiiiiiiiiiiiiii   "+newUser.getUsername()+" "+newUser.getEmail());
        Optional<User> existingEmail = userRepo.findByEmail(user.getEmail());

        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
        if (existingEmail.isPresent())
            return "This email already exists, please use other";
        if (existingUser.isPresent())
            return "This username already exists, please use other";

        System.out.println("helooooooo "+newUser.getUsername()+" "+newUser.getEmail()+" "+newUser.getPassword());

        userRepo.save(newUser);
        return "User created successfully";

    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepo.findByUsername(username);

        if (!user.isPresent())
            return null;

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());

    }

    public Map<String, String> loginByUserName(UserLoginDTO credentials){
        String username = credentials.getUserName();
        String password = credentials.getPassword();

        Optional<User> user = userRepo.findByUsername(username);

        if (!user.isPresent()) {
            System.out.println("not founnnnnnnnnnnd "+username+" "+password);
            Map<String, String> m = new HashMap<>();
            m.put("Can't find profile for these credentials!", username);
            return m;
        } else {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            String token = jwtService.generateToken(credentials);
            Map<String, String> response = new HashMap<>();
            response.put("Token", token);

            return response;
        }
    }


}
