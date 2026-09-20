package com.faisalrmdhn.GaraseKu.model.dto.payloads.users;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordRequest(String currentPassword, String newPassword, String confirmNewPassword) {
  public UpdateUserPasswordRequest(
      @NotBlank(message = "Current password cannot be blank") String currentPassword,
      @NotBlank(message = "New password cannot be blank") @Size(min = 8, max = 100, message = "Password must have at least 8 characters.") @Pattern(regexp = "^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must include uppercase, lowercase, and a number.") String newPassword,
      @NotBlank(message = "Confirm new password cannot be blank") String confirmNewPassword) {
    this.currentPassword = Objects.requireNonNull(currentPassword, "Current password cannot be null");
    this.newPassword = Objects.requireNonNull(newPassword, "New password cannot be null");
    this.confirmNewPassword = Objects.requireNonNull(confirmNewPassword, "Confirm new password cannot be null");
  }
}
