package com.bhagyashri.codebproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bhagyashri.codebproject.dao.RegisterRequest;
import com.bhagyashri.codebproject.entity.User;
import com.bhagyashri.codebproject.entity.User.Role;
import com.bhagyashri.codebproject.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            return "Email already exists";

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER); // Default role for new users

        userRepository.save(user);
        return "User registered successfully.";
    }
}
