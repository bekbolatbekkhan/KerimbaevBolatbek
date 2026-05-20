package com.lab.KerimbaevBolatbek.controller;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekLoginRequest;
import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekUserRegisterRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekAuthResponse;
import com.lab.KerimbaevBolatbek.service.KerimbaevBolatbekAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class KerimbaevBolatbekAuthController {

    private final KerimbaevBolatbekAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<KerimbaevBolatbekAuthResponse> register(
            @Valid @RequestBody KerimbaevBolatbekUserRegisterRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<KerimbaevBolatbekAuthResponse> login(
            @Valid @RequestBody KerimbaevBolatbekLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}