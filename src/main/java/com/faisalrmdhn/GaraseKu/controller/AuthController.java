package com.faisalrmdhn.GaraseKu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faisalrmdhn.GaraseKu.model.dto.payloads.LoginRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.RefreshTokenRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.RegisterRequest;
import com.faisalrmdhn.GaraseKu.model.dto.responses.ApiResponse;
import com.faisalrmdhn.GaraseKu.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse> login(@Valid @RequestBody(required = true) LoginRequest loginRequest) {
    return authService.login(loginRequest);
  }

  @PostMapping("/register")
  public ResponseEntity<ApiResponse> register(@Valid @RequestBody(required = true) RegisterRequest registerRequest) {
    return authService.register(registerRequest);
  }

  @GetMapping("/me")
  public ResponseEntity<ApiResponse> me(HttpServletRequest httpServletRequest) {
    return authService.me(httpServletRequest);
  }

  @PostMapping("/refresh")
  public ResponseEntity<ApiResponse> refresh(
      @Valid @RequestBody(required = true) RefreshTokenRequest refreshTokenRequest) {
    return authService.refresh(refreshTokenRequest);
  }
}