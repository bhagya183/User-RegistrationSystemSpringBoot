package com.bhagyashri.codebproject.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bhagyashri.codebproject.dao.AuthResponse;
import com.bhagyashri.codebproject.dao.ForgotPasswordRequest;
import com.bhagyashri.codebproject.dao.LoginRequest;
import com.bhagyashri.codebproject.dao.PasswordResetRequest;
import com.bhagyashri.codebproject.dao.RegisterRequest;
import com.bhagyashri.codebproject.entity.PasswordResetToken;
import com.bhagyashri.codebproject.entity.User;
import com.bhagyashri.codebproject.repository.PasswordResetTokenRepository;
import com.bhagyashri.codebproject.repository.UserRepository;
import com.bhagyashri.codebproject.service.AuthService;
import com.bhagyashri.codebproject.service.EmailService;
import com.bhagyashri.codebproject.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;
    
    @Autowired 
    private AuthService authService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordResetTokenRepository tokenRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String result = userService.register(request);
        if (result.equals("Email already exists")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            logger.info("Received login request for user: {}", request.getEmail());
            AuthResponse response = authService.login(request);
            logger.info("Login successful for user: {}", request.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Login failed for user: " + request.getEmail(), e);
            return ResponseEntity.status(500)
                .body("Login failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found");
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(email);
        resetToken.setToken(token);
        resetToken.setExpiryDate(expiry);
        tokenRepository.save(resetToken);

        String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + token;
        emailService.sendResetLink(email, resetLink);

        return ResponseEntity.ok("Reset link sent to your email.");
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        String token = request.getToken();
        String newPassword = request.getPassword();

        if (token == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Token and new password are required");
        }

        Optional<PasswordResetToken> tokenEntry = tokenRepository.findByToken(token);
        if (tokenEntry.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        PasswordResetToken resetToken = tokenEntry.get();

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token expired");
        }

        User user = userRepository.findByEmail(resetToken.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);

        return ResponseEntity.ok("Password reset successful");
    }
}

