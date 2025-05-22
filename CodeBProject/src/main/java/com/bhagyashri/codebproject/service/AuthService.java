package com.bhagyashri.codebproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bhagyashri.codebproject.dao.AuthResponse;
import com.bhagyashri.codebproject.dao.LoginRequest;
import com.bhagyashri.codebproject.security.JwtUtil;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;

    public AuthResponse login(LoginRequest request) {
        try {
            logger.info("Attempting login for user: {}", request.getEmail());
            
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            logger.info("Authentication successful for user: {}", request.getEmail());

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            logger.info("User details loaded successfully for user: {}", request.getEmail());
            
            String token = jwtUtil.generateToken(userDetails);
            logger.info("JWT token generated successfully");
            
            // Extract the first role from authorities
            String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");
            logger.info("User role extracted: {}", role);

            return new AuthResponse(token, role);
        } catch (Exception e) {
            logger.error("Error during login process: ", e);
            throw e;
        }
    }
}
