package com.appbit.auth.controller;

import com.appbit.auth.dto.LoginRequestDto;
import com.appbit.auth.dto.RefreshRequestDto;
import com.appbit.auth.dto.RegisterRequestDto;
import com.appbit.auth.dto.TokenResponseDto;
import com.appbit.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, login and token refresh endpoints")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user and return JWT tokens")
    public ResponseEntity<TokenResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user and return JWT tokens")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh the access token using a valid refresh token")
    public ResponseEntity<TokenResponseDto> refresh(@Valid @RequestBody RefreshRequestDto request) {
        return ResponseEntity.ok(authService.refresh(request.getRefreshToken()));
    }
}
