package com.bhagyashri.codebproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bhagyashri.codebproject.dao.RegisterRequest;
import com.bhagyashri.codebproject.entity.User;
import com.bhagyashri.codebproject.entity.User.Role;
import com.bhagyashri.codebproject.repository.UserRepository;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;

    public String register(RegisterRequest request) {
        logger.info("Attempting to register user with email: {} and role: {}", request.getEmail(), request.getRole());
        
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Registration failed: Email {} already exists", request.getEmail());
            return "Email already exists";
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf("ROLE_" + request.getRole()));
        
        logger.info("Creating new user with role: {}", user.getRole());
        
        userRepository.save(user);
        logger.info("User registered successfully with email: {}", request.getEmail());

        // Send verification email
        try {
            String verificationLink = "http://localhost:8080/api/auth/verify?email=" + user.getEmail();
            emailService.sendVerificationEmail(user.getEmail(), verificationLink);
            logger.info("Verification email sent to: {}", user.getEmail());
        } catch (Exception e) {
            logger.error("Failed to send verification email to: " + user.getEmail(), e);
            // Don't throw the exception, just log it
        }

        return "User registered successfully.";
    }
}
