package com.faisalrmdhn.GaraseKu.service;

import org.springframework.http.ResponseEntity;

import com.faisalrmdhn.GaraseKu.model.dto.payloads.LoginRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.RegisterRequest;
import com.faisalrmdhn.GaraseKu.model.dto.responses.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
  ResponseEntity<ApiResponse> register(RegisterRequest registerRequest);

  ResponseEntity<ApiResponse> login(LoginRequest loginRequest);

  ResponseEntity<ApiResponse> me(HttpServletRequest request);
}
