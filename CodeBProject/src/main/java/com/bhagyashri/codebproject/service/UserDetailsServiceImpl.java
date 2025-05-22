package com.bhagyashri.codebproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bhagyashri.codebproject.entity.User;
import com.bhagyashri.codebproject.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            logger.info("Loading user by email: {}", email);
            
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
            
            logger.info("User found: {}, Role: {}", user.getEmail(), user.getRole());
            
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
            
            logger.info("UserDetails created successfully");
            return userDetails;
        } catch (Exception e) {
            logger.error("Error loading user by email {}: {}", email, e.getMessage());
            throw e;
        }
    }
} 