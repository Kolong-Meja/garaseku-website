package com.faisalrmdhn.GaraseKu.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faisalrmdhn.GaraseKu.exception.BadRequestException;
import com.faisalrmdhn.GaraseKu.exception.UnauthorizedException;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.LoginRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.RefreshTokenRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.RegisterRequest;
import com.faisalrmdhn.GaraseKu.model.dto.responses.ApiResponse;
import com.faisalrmdhn.GaraseKu.model.dto.responses.AuthenticatedUserResponse;
import com.faisalrmdhn.GaraseKu.model.dto.responses.AuthenticationResponse;
import com.faisalrmdhn.GaraseKu.model.entity.MasterRole;
import com.faisalrmdhn.GaraseKu.model.entity.MasterUser;
import com.faisalrmdhn.GaraseKu.model.entity.MasterUserPk;
import com.faisalrmdhn.GaraseKu.repository.MasterRoleRepository;
import com.faisalrmdhn.GaraseKu.repository.MasterUserRepository;
import com.faisalrmdhn.GaraseKu.security.JwtAuthenticationHandler;
import com.faisalrmdhn.GaraseKu.service.AuthService;
import com.faisalrmdhn.GaraseKu.util.IdGenerator;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

  private static final ZoneId APPLICATION_ZONE = ZoneId.of("Asia/Jakarta");
  private static final DateTimeFormatter RESPONSE_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static final String DEFAULT_ROLE = "user";

  private final MasterUserRepository masterUserRepository;
  private final MasterRoleRepository masterRoleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtAuthenticationHandler jwtAuthenticationHandler;
  private final IdGenerator idGenerator;

  public AuthServiceImpl(
      MasterUserRepository masterUserRepository,
      MasterRoleRepository masterRoleRepository,
      PasswordEncoder passwordEncoder,
      JwtAuthenticationHandler jwtAuthenticationHandler,
      IdGenerator idGenerator) {
    this.masterUserRepository = masterUserRepository;
    this.masterRoleRepository = masterRoleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtAuthenticationHandler = jwtAuthenticationHandler;
    this.idGenerator = idGenerator;
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity<ApiResponse> login(LoginRequest loginRequest) {
    LOGGER.info("[GARASEKU LOG]: Attempting login.");
    String email = normalizeEmail(loginRequest.email());
    MasterUser user = masterUserRepository.findByEmail(email)
        .orElseThrow(() -> new UnauthorizedException("Invalid email or password."));

    if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
      LOGGER.error("[GARASEKU LOG]: Invalid email or password.");
      throw new UnauthorizedException("Invalid email or password.");
    }

    LOGGER.info("[GARASEKU LOG]: Login successful.");
    AuthenticationResponse authentication = createAuthenticationResponse(user);
    return response(HttpStatus.OK, "Login successful.", authentication);
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity<ApiResponse> me(HttpServletRequest request) {
    LOGGER.info("[GARASEKU LOG]: Retrieving authenticated user information.");

    if (request.getUserPrincipal() == null || request.getUserPrincipal().getName() == null) {
      LOGGER.error("[GARASEKU LOG]: Authentication is required.");
      throw new UnauthorizedException("Authentication is required.");
    }

    MasterUser user = masterUserRepository.findByEmailWithProfile(request.getUserPrincipal().getName())
        .orElseThrow(() -> new UnauthorizedException("Authenticated user no longer exists."));

    LOGGER.info("[GARASEKU LOG]: Authenticated user retrieved successfully.");
    return response(HttpStatus.OK, "Authenticated user retrieved successfully.", toUserResponse(user));
  }

  @Override
  @Transactional
  public ResponseEntity<ApiResponse> register(RegisterRequest registerRequest) {
    LOGGER.info("[GARASEKU LOG]: Attempting registration.");
    String username = normalizeUsername(registerRequest.username());
    String email = normalizeEmail(registerRequest.email());

    if (masterUserRepository.existsByVusernameIgnoreCase(username)) {
      LOGGER.error("[GARASEKU LOG]: Username is already registered.");
      throw new BadRequestException("Username is already registered.");
    }
    if (masterUserRepository.existsByVemailIgnoreCase(email)) {
      LOGGER.error("[GARASEKU LOG]: Email is already registered.");
      throw new BadRequestException("Email is already registered.");
    }

    LocalDateTime now = LocalDateTime.now(APPLICATION_ZONE);
    MasterRole defaultRole = masterRoleRepository.findByVrolenameIgnoreCase(DEFAULT_ROLE)
        .orElseGet(() -> createDefaultRole(now));

    MasterUser user = new MasterUser();
    user.setMasterUserPk(new MasterUserPk(idGenerator.generate()));
    user.setVusername(username);
    user.setVemail(email);
    user.setVphonenumber(registerRequest.phoneNumber().trim());
    user.setVpassword(passwordEncoder.encode(registerRequest.password()));
    user.setVfullname(registerRequest.fullname().trim());
    user.setRoles(new LinkedHashSet<>(Set.of(defaultRole)));
    user.setCreatedBy(username);
    user.setCreatedAt(now);

    try {
      MasterUser savedUser = masterUserRepository.saveAndFlush(user);
      AuthenticationResponse authentication = createAuthenticationResponse(savedUser);
      LOGGER.info("[GARASEKU LOG]: Registration successful.");
      return response(HttpStatus.CREATED, "Registration successful.", authentication);
    } catch (DataIntegrityViolationException exception) {
      LOGGER.error("[GARASEKU LOG]: Username or email is already registered.");
      throw new BadRequestException("Username or email is already registered.");
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity<ApiResponse> refresh(RefreshTokenRequest refreshTokenRequest) {
    LOGGER.info("[GARASEKU LOG]: Attempting to refresh token.");
    String refreshToken = refreshTokenRequest.refreshToken().trim();
    String email = jwtAuthenticationHandler.extractUsername(refreshToken);
    MasterUser user = masterUserRepository.findByEmail(email)
        .orElseThrow(() -> new UnauthorizedException("Invalid or expired refresh token."));

    if (!jwtAuthenticationHandler.isRefreshTokenValid(refreshToken, user)) {
      LOGGER.error("[GARASEKU LOG]: Invalid or expired refresh token.");
      throw new UnauthorizedException("Invalid or expired refresh token.");
    }

    AuthenticationResponse authentication = createAuthenticationResponse(user);
    LOGGER.info("[GARASEKU LOG]: Token refreshed successfully.");
    return response(HttpStatus.OK, "Token refreshed successfully.", authentication);
  }

  private MasterRole createDefaultRole(LocalDateTime now) {
    MasterRole role = new MasterRole(
        idGenerator.generate(),
        DEFAULT_ROLE,
        "Default role for registered users.",
        new LinkedHashSet<>());
    role.setCreatedBy("SYSTEM");
    role.setCreatedAt(now);
    return masterRoleRepository.save(role);
  }

  private AuthenticationResponse createAuthenticationResponse(MasterUser user) {
    return new AuthenticationResponse(
        jwtAuthenticationHandler.generateAccessToken(user),
        jwtAuthenticationHandler.generateRefreshToken(user),
        "Bearer",
        toUserResponse(user));
  }

  private AuthenticatedUserResponse toUserResponse(MasterUser user) {
    Set<String> roles = user.getRoles().stream()
        .map(MasterRole::getVrolename)
        .collect(Collectors.toCollection(LinkedHashSet::new));

    Map<String, String> settings = user.getSettings().stream()
        .collect(Collectors.toMap(
            setting -> setting.getVsettingkey(),
            setting -> setting.getVsettingvalue(),
            (first, second) -> second,
            LinkedHashMap::new));

    return new AuthenticatedUserResponse(
        user.getMasterUserPk().getVuserid(),
        user.getVusername(),
        user.getVemail(),
        user.getVphonenumber(),
        user.getVfullname(),
        roles,
        settings);
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

  private String normalizeUsername(String username) {
    return username.trim().toLowerCase(Locale.ROOT);
  }

  private String normalizeEmail(String email) {
    return email.trim().toLowerCase(Locale.ROOT);
  }
}