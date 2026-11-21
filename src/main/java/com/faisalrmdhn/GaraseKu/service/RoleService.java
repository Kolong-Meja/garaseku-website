package com.faisalrmdhn.GaraseKu.service;

import org.springframework.http.ResponseEntity;

import com.faisalrmdhn.GaraseKu.model.dto.payloads.PaginationRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.SearchRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.roles.CreateRoleRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.roles.UpdateRoleRequest;
import com.faisalrmdhn.GaraseKu.model.dto.responses.ApiResponse;

public interface RoleService {
  ResponseEntity<ApiResponse> findAll(SearchRequest searchRequest, PaginationRequest paginationRequest);

  ResponseEntity<ApiResponse> findById(String vuserid);

  ResponseEntity<ApiResponse> createData(CreateRoleRequest createRoleRequest);

  ResponseEntity<ApiResponse> updateData(UpdateRoleRequest updateRoleRequest, String vroleid);

  ResponseEntity<ApiResponse> deleteData(String vroleid);
}
