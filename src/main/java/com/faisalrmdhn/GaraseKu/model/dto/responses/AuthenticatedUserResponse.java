package com.faisalrmdhn.GaraseKu.model.dto.responses;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record AuthenticatedUserResponse(
    String userId,
    String username,
    String email,
    String phoneNumber,
    String fullname,
    Set<String> roles,
    Map<String, String> settings) {
  public AuthenticatedUserResponse(
      String userId,
      String username,
      String email,
      String phoneNumber,
      String fullname,
      Set<String> roles,
      Map<String, String> settings) {
    this.userId = Objects.requireNonNull(userId, "User ID cannot be null.");
    this.username = Objects.requireNonNull(username, "Username cannot be null.");
    this.email = Objects.requireNonNull(email, "Email cannot be null.");
    this.phoneNumber = Objects.requireNonNull(phoneNumber, "Phone Number cannot be null.");
    this.fullname = Objects.requireNonNull(fullname, "Fullname cannot be null.");
    this.roles = Objects.requireNonNull(roles, "Roles cannot be null.");

    this.settings = settings;
  }
}