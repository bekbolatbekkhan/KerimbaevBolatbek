package com.lab.KerimbaevBolatbek.service;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekLoginRequest;
import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekUserRegisterRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekAuthResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekUser;
import com.lab.KerimbaevBolatbek.entity.enums.KerimbaevBolatbekRole;
import com.lab.KerimbaevBolatbek.exception.KerimbaevBolatbekBadRequestException;
import com.lab.KerimbaevBolatbek.repository.KerimbaevBolatbekUserRepository;
import com.lab.KerimbaevBolatbek.security.KerimbaevBolatbekJwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KerimbaevBolatbekAuthService {

    private final KerimbaevBolatbekUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final KerimbaevBolatbekJwtUtil jwtUtil;
    private final KerimbaevBolatbekNotificationService notificationService;

    public KerimbaevBolatbekAuthResponse register(KerimbaevBolatbekUserRegisterRequest request) {
        log.info("Registering new user: {}", request.getUsername());

        // check for duplicates
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new KerimbaevBolatbekBadRequestException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new KerimbaevBolatbekBadRequestException("Email already exists");
        }

        // create user
        KerimbaevBolatbekUser user = KerimbaevBolatbekUser.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .role(KerimbaevBolatbekRole.ROLE_USER)
                .enabled(true)
                .build();

        userRepository.save(user);

        log.info("User registered successfully: {}", user.getUsername());
        notificationService.sendWelcomeEmail(user.getEmail(), user.getUsername());

        // generate JWT
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();

        String token = jwtUtil.generateToken(userDetails);

        return new KerimbaevBolatbekAuthResponse(token, user.getUsername(), user.getRole().name());
    }

    public KerimbaevBolatbekAuthResponse login(KerimbaevBolatbekLoginRequest request) {
        log.info("Login attempt for user: {}", request.getUsername());

        // authenticate (throws BadCredentialsException if wrong)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        KerimbaevBolatbekUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new KerimbaevBolatbekBadRequestException("User not found"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();

        String token = jwtUtil.generateToken(userDetails);
        log.info("User logged in successfully: {}", user.getUsername());

        return new KerimbaevBolatbekAuthResponse(token, user.getUsername(), user.getRole().name());
    }
}

