package com.faisalrmdhn.GaraseKu.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faisalrmdhn.GaraseKu.model.dto.RoleDTO;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.PaginationRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.SearchRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.roles.CreateRoleRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.roles.UpdateRoleRequest;
import com.faisalrmdhn.GaraseKu.model.dto.responses.ApiResponse;
import com.faisalrmdhn.GaraseKu.model.dto.responses.PaginationResponse;
import com.faisalrmdhn.GaraseKu.model.entity.MasterRole;
import com.faisalrmdhn.GaraseKu.repository.MasterRoleRepository;
import com.faisalrmdhn.GaraseKu.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
  private static final ZoneId APPLICATION_ZONE = ZoneId.of("Asia/Jakarta");
  private static final DateTimeFormatter RESPONSE_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private static final int DEFAULT_PAGE = 0;
  private static final int DEFAULT_SIZE = 10;
  private static final int MAX_PAGE_SIZE = 100;

  private static final Map<String, String> ROLE_SORT_FIELDS = Map.of(
      "id", "vroleid",
      "name", "vrolename",
      "createdAt", "createdAt",
      "updatedAt", "updatedAt");

  private final MasterRoleRepository masterRoleRepository;

  public RoleServiceImpl(MasterRoleRepository masterRoleRepository) {
    this.masterRoleRepository = masterRoleRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity<ApiResponse> findAll(
      SearchRequest searchRequest,
      PaginationRequest paginationRequest) {
    LOGGER.info("Finding roles");
    String query = searchRequest == null
        ? ""
        : searchRequest.query().trim();

    Pageable pageable = buildPageable(paginationRequest);
    Page<MasterRole> rolePage = masterRoleRepository.searchRoles(
        query,
        pageable);

    if (rolePage.isEmpty()) {
      LOGGER.info("No roles found");
      return response(
          HttpStatus.NOT_FOUND,
          "No roles found",
          Collections.emptyList());
    }

    List<String> roleIds = rolePage.getContent()
        .stream()
        .map(MasterRole::getVroleid)
        .toList();

    List<MasterRole> rolesWithUsers = masterRoleRepository.findAllWithUsersByVroleidIn(roleIds);

    Map<String, MasterRole> rolesMap = rolesWithUsers
        .stream()
        .collect(Collectors.toMap(
            MasterRole::getVroleid,
            Function.identity()));

    List<RoleDTO> roles = rolePage.getContent()
        .stream()
        .map(role -> RoleDTO.fromObject(
            rolesMap.get(role.getVroleid())))
        .toList();

    PaginationResponse<RoleDTO> paginationResponse = new PaginationResponse<>(
        roles,
        rolePage.getTotalPages(),
        rolePage.getTotalElements(),
        rolePage.getSize(),
        rolePage.getNumber(),
        rolePage.hasNext());

    LOGGER.info(
        "Roles found: {}, page: {}, total pages: {}",
        rolePage.getNumberOfElements(),
        rolePage.getNumber(),
        rolePage.getTotalPages());
    return response(
        HttpStatus.OK,
        "Roles found",
        paginationResponse);
  }

  @Override
  public ResponseEntity<ApiResponse> findById(String vuserid) {
    LOGGER.info("Finding role by ID: {}", vuserid);
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public ResponseEntity<ApiResponse> createData(CreateRoleRequest createRoleRequest) {
    LOGGER.info("Creating new role");
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createData'");
  }

  @Override
  public ResponseEntity<ApiResponse> updateData(UpdateRoleRequest updateRoleRequest, String vroleid) {
    LOGGER.info("Updating role with ID: {}", vroleid);
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateData'");
  }

  @Override
  public ResponseEntity<ApiResponse> deleteData(String vroleid) {
    LOGGER.info("Deleting role with ID: {}", vroleid);
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteData'");
  }

  private ResponseEntity<ApiResponse> response(HttpStatus status, String message, Object resource) {
    ApiResponse body = new ApiResponse(
        status.value(),
        status.is2xxSuccessful(),
        message,
        LocalDateTime.now(APPLICATION_ZONE).format(RESPONSE_TIMESTAMP_FORMAT),
        resource);
    return ResponseEntity.status(status).body(body);
  }

  private Pageable buildPageable(PaginationRequest request) {
    int page = Math.max(request.page(), DEFAULT_PAGE);
    int size = request.size() <= 0
        ? DEFAULT_SIZE
        : Math.min(request.size(), MAX_PAGE_SIZE);

    String sortField = ROLE_SORT_FIELDS.getOrDefault(
        request.sortField(),
        "vrolename");
    Sort.Direction direction = "desc".equalsIgnoreCase(request.direction())
        ? Sort.Direction.DESC
        : Sort.Direction.ASC;

    return PageRequest.of(
        page,
        size,
        Sort.by(direction, sortField));
  }
}
