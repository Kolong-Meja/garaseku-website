package com.faisalrmdhn.GaraseKu.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.faisalrmdhn.GaraseKu.model.dto.payloads.LoginRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.RegisterRequest;
import com.faisalrmdhn.GaraseKu.model.dto.responses.ApiResponse;
import com.faisalrmdhn.GaraseKu.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {

  @Override
  public ResponseEntity<ApiResponse> login(LoginRequest loginRequest) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> me(HttpServletRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> register(RegisterRequest registerRequest) {
    // TODO Auto-generated method stub
    return null;
  }

}
