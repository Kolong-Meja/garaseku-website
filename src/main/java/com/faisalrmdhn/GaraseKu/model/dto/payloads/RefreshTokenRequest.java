package com.faisalrmdhn.GaraseKu.model.dto.payloads;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(String refreshToken) {
  public RefreshTokenRequest(
      @NotBlank(message = "Refresh token cannot be blank.") String refreshToken) {
    this.refreshToken = Objects.requireNonNull(refreshToken, "Refresh token cannot be null.");
  }
}