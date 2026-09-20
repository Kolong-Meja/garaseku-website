package com.faisalrmdhn.GaraseKu.service;

import org.springframework.http.ResponseEntity;

import com.faisalrmdhn.GaraseKu.model.dto.payloads.PaginationRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.SearchRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.users.UpdateUserPasswordRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.users.UpdateUserRequest;
import com.faisalrmdhn.GaraseKu.model.dto.responses.ApiResponse;

public interface UserService {
  ResponseEntity<ApiResponse> findAll(SearchRequest searchRequest, PaginationRequest paginationRequest);

  ResponseEntity<ApiResponse> findById(String vuserid);

  ResponseEntity<ApiResponse> findByUsername(String vusername);

  ResponseEntity<ApiResponse> findByEmail(String vemail);

  ResponseEntity<ApiResponse> findByRole(String role);

  ResponseEntity<ApiResponse> findByFullname(String vfullname);

  boolean usernameExists(String vusername);

  boolean emailExists(String vemail);

  ResponseEntity<ApiResponse> findSettingsByUserId(String vuserid);

  ResponseEntity<ApiResponse> findSetting(String vuserid, String vsettingkey);

  ResponseEntity<ApiResponse> upsertSetting(String vuserid, String vsettingkey, String vsettingvalue);

  ResponseEntity<ApiResponse> deleteSetting(String vuserid, String vsettingkey);

  ResponseEntity<ApiResponse> deleteAllSettings(String vuserid);

  ResponseEntity<ApiResponse> updateData(UpdateUserRequest updateUserRequest, String vuserid);

  ResponseEntity<ApiResponse> updatePassword(UpdateUserPasswordRequest updateUserPasswordRequest, String vuserid);

  ResponseEntity<ApiResponse> deleteData(String vuserid);
}
