package com.faisalrmdhn.GaraseKu.model.dto.responses;

import java.util.Objects;

public record AuthenticationResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    AuthenticatedUserResponse user) {
  public AuthenticationResponse(
      String accessToken,
      String refreshToken,
      String tokenType,
      AuthenticatedUserResponse user) {
    this.accessToken = Objects.requireNonNull(accessToken, "Access token cannot be null.");
    this.refreshToken = Objects.requireNonNull(refreshToken, "Refresh token cannot be null.");
    this.tokenType = Objects.requireNonNull(tokenType, "Token type cannot be null.");
    this.user = Objects.requireNonNull(user, "User cannot be null.");
  }
}
