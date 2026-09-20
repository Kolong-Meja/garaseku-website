package com.faisalrmdhn.GaraseKu.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.faisalrmdhn.GaraseKu.model.dto.payloads.PaginationRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.SearchRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.users.UpdateUserPasswordRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.users.UpdateUserRequest;
import com.faisalrmdhn.GaraseKu.model.dto.responses.ApiResponse;
import com.faisalrmdhn.GaraseKu.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Override
  public ResponseEntity<ApiResponse> deleteAllSettings(String vuserid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> deleteData(String vuserid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> deleteSetting(String vuserid, String vsettingkey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean emailExists(String vemail) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public ResponseEntity<ApiResponse> findAll(SearchRequest searchRequest, PaginationRequest paginationRequest) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> findByEmail(String vemail) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> findByFullname(String vfullname) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> findById(String vuserid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> findByRole(String role) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> findByUsername(String vusername) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> findSetting(String vuserid, String vsettingkey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> findSettingsByUserId(String vuserid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> updateData(UpdateUserRequest updateUserRequest, String vuserid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> updatePassword(UpdateUserPasswordRequest updateUserPasswordRequest,
      String vuserid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ResponseEntity<ApiResponse> upsertSetting(String vuserid, String vsettingkey, String vsettingvalue) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean usernameExists(String vusername) {
    // TODO Auto-generated method stub
    return false;
  }

}
