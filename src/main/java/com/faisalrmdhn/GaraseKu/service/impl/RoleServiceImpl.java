package com.faisalrmdhn.GaraseKu.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.faisalrmdhn.GaraseKu.model.dto.payloads.PaginationRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.SearchRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.roles.CreateRoleRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.roles.UpdateRoleRequest;
import com.faisalrmdhn.GaraseKu.model.dto.responses.ApiResponse;
import com.faisalrmdhn.GaraseKu.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

  @Override
  public ResponseEntity<ApiResponse> findAll(SearchRequest searchRequest, PaginationRequest paginationRequest) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public ResponseEntity<ApiResponse> findById(String vuserid) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public ResponseEntity<ApiResponse> createData(CreateRoleRequest createRoleRequest) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createData'");
  }

  @Override
  public ResponseEntity<ApiResponse> updateData(UpdateRoleRequest updateRoleRequest, String vroleid) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateData'");
  }

  @Override
  public ResponseEntity<ApiResponse> deleteData(String vroleid) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteData'");
  }

}
