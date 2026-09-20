package com.faisalrmdhn.GaraseKu.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.faisalrmdhn.GaraseKu.exception.BadRequestException;
import com.faisalrmdhn.GaraseKu.exception.UnauthorizedException;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.LoginRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.RefreshTokenRequest;
import com.faisalrmdhn.GaraseKu.model.dto.payloads.RegisterRequest;
import com.faisalrmdhn.GaraseKu.model.dto.responses.AuthenticatedUserResponse;
import com.faisalrmdhn.GaraseKu.model.dto.responses.AuthenticationResponse;
import com.faisalrmdhn.GaraseKu.model.entity.MasterRole;
import com.faisalrmdhn.GaraseKu.model.entity.MasterUser;
import com.faisalrmdhn.GaraseKu.model.entity.MasterUserPk;
import com.faisalrmdhn.GaraseKu.repository.MasterRoleRepository;
import com.faisalrmdhn.GaraseKu.repository.MasterUserRepository;
import com.faisalrmdhn.GaraseKu.security.JwtAuthenticationHandler;
import com.faisalrmdhn.GaraseKu.util.IdGenerator;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
  @Mock
  private MasterUserRepository masterUserRepository;

  @Mock
  private MasterRoleRepository masterRoleRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JwtAuthenticationHandler jwtAuthenticationHandler;

  @Mock
  private IdGenerator idGenerator;

  @Mock
  private HttpServletRequest request;

  private AuthServiceImpl authService;

  @BeforeEach
  void setUp() {
    authService = new AuthServiceImpl(
        masterUserRepository,
        masterRoleRepository,
        passwordEncoder,
        jwtAuthenticationHandler,
        idGenerator);
  }

  @Test
  void registerCreatesUserWithEncodedPasswordAndDefaultRole() {
    RegisterRequest payload = new RegisterRequest(
        "faisal_dev",
        "FAISAL@GARASEKU.CO.ID",
        "081234567890",
        "Strong123",
        "Faisal Ramadhan");
    MasterRole userRole = new MasterRole("role-user", "user", "Default user");

    when(masterUserRepository.existsByVusernameIgnoreCase("faisal_dev")).thenReturn(false);
    when(masterUserRepository.existsByVemailIgnoreCase("faisal@garaseku.co.id")).thenReturn(false);
    when(masterRoleRepository.findByVrolenameIgnoreCase("user")).thenReturn(Optional.of(userRole));
    when(idGenerator.generate()).thenReturn("user-1");
    when(passwordEncoder.encode("Strong123")).thenReturn("encoded-password");
    when(masterUserRepository.saveAndFlush(any(MasterUser.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
    when(jwtAuthenticationHandler.generateAccessToken(any(MasterUser.class))).thenReturn("access-token");
    when(jwtAuthenticationHandler.generateRefreshToken(any(MasterUser.class))).thenReturn("refresh-token");

    var response = authService.register(payload);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    AuthenticationResponse resource = (AuthenticationResponse) response.getBody().resource();
    assertEquals("access-token", resource.accessToken());
    assertEquals("faisal@garaseku.co.id", resource.user().email());
    assertEquals(Set.of("user"), resource.user().roles());

    ArgumentCaptor<MasterUser> userCaptor = ArgumentCaptor.forClass(MasterUser.class);
    verify(masterUserRepository).saveAndFlush(userCaptor.capture());
    assertEquals("encoded-password", userCaptor.getValue().getPassword());
  }

  @Test
  void registerRejectsExistingUsername() {
    RegisterRequest payload = new RegisterRequest(
        "existing_user",
        "new@garaseku.co.id",
        "081234567890",
        "Strong123",
        "Existing User");
    when(masterUserRepository.existsByVusernameIgnoreCase("existing_user")).thenReturn(true);

    assertThrows(BadRequestException.class, () -> authService.register(payload));
  }

  @Test
  void loginRejectsInvalidPasswordWithoutLeakingWhichCredentialFailed() {
    MasterUser user = user();
    when(masterUserRepository.findByEmail("faisal@garaseku.co.id")).thenReturn(Optional.of(user));
    when(passwordEncoder.matches("Wrong123", "encoded-password")).thenReturn(false);

    UnauthorizedException exception = assertThrows(
        UnauthorizedException.class,
        () -> authService.login(new LoginRequest("faisal@garaseku.co.id", "Wrong123")));

    assertEquals("Invalid email or password.", exception.getMessage());
  }

  @Test
  void meReturnsOnlySafeProfileFields() {
    MasterUser user = user();
    Principal principal = () -> "faisal@garaseku.co.id";
    when(request.getUserPrincipal()).thenReturn(principal);
    when(masterUserRepository.findByEmailWithProfile("faisal@garaseku.co.id"))
        .thenReturn(Optional.of(user));

    var response = authService.me(request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    AuthenticatedUserResponse resource = (AuthenticatedUserResponse) response.getBody().resource();
    assertEquals("faisal_dev", resource.username());
    assertEquals("faisal@garaseku.co.id", resource.email());
  }

  @Test
  void refreshIssuesNewAccessAndRefreshTokens() {
    MasterUser user = user();
    when(jwtAuthenticationHandler.extractUsername("valid-refresh-token"))
        .thenReturn("faisal@garaseku.co.id");
    when(masterUserRepository.findByEmail("faisal@garaseku.co.id")).thenReturn(Optional.of(user));
    when(jwtAuthenticationHandler.isRefreshTokenValid("valid-refresh-token", user)).thenReturn(true);
    when(jwtAuthenticationHandler.generateAccessToken(user)).thenReturn("new-access-token");
    when(jwtAuthenticationHandler.generateRefreshToken(user)).thenReturn("new-refresh-token");

    var response = authService.refresh(new RefreshTokenRequest("valid-refresh-token"));

    assertTrue(response.getBody().success());
    AuthenticationResponse resource = (AuthenticationResponse) response.getBody().resource();
    assertEquals("new-access-token", resource.accessToken());
    assertEquals("new-refresh-token", resource.refreshToken());
  }

  private MasterUser user() {
    MasterRole role = new MasterRole("role-user", "user", "Default user");
    MasterUser user = new MasterUser();
    user.setMasterUserPk(new MasterUserPk("user-1"));
    user.setVusername("faisal_dev");
    user.setVemail("faisal@garaseku.co.id");
    user.setVphonenumber("081234567890");
    user.setVpassword("encoded-password");
    user.setVfullname("Faisal Ramadhan");
    user.setRoles(Set.of(role));
    return user;
  }
}
