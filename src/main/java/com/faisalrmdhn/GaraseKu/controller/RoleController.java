package com.faisalrmdhn.GaraseKu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faisalrmdhn.GaraseKu.model.dto.payloads.PaginationRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.SearchRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.roles.CreateRoleRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.roles.UpdateRoleRequest;
import com.faisalrmdhn.GaraseKu.model.dto.responses.ApiResponse;
import com.faisalrmdhn.GaraseKu.service.RoleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/roles")
public class RoleController {
  private final RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @GetMapping("/")
  public ResponseEntity<ApiResponse> getAll(
      @RequestParam(value = "q", required = false, defaultValue = "") SearchRequest searchRequest,
      @RequestParam(required = false) PaginationRequest paginationRequest) {
    return roleService.findAll(searchRequest, paginationRequest);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse> getOne(@PathVariable String id) {
    return roleService.findById(id);
  }

  @PostMapping("/")
  public ResponseEntity<ApiResponse> save(
      @Valid @RequestBody(required = true) CreateRoleRequest createRoleRequest) {
    return roleService.createData(createRoleRequest);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse> update(
      HttpServletRequest httpServletRequest,
      @PathVariable String id,
      @Valid @RequestBody(required = false) UpdateRoleRequest updateRoleRequest) {
    return roleService.updateData(updateRoleRequest, id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse> delete(@PathVariable String id) {
    return roleService.deleteData(id);
  }
}
