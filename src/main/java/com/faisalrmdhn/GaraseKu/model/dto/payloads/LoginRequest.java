package com.faisalrmdhn.GaraseKu.model.dto.payloads;

import java.util.Objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(String email, String password) {
  public LoginRequest(
      @NotBlank(message = "Email cannot be blank.") @Size(max = 100, message = "Email only has a maximum of 100 characters.") @Email(message = "Email should be valid and include @.") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.co\\\\.id$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email must end with .co.id") String email,
      @NotBlank(message = "Password cannot be blank.") @Size(min = 8, max = 100, message = "Password must have at least 8 characters.") @Pattern(regexp = "^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must include uppercase, lowercase, and a number.") String password) {
    this.email = Objects.requireNonNull(email, "Email cannot be null.");
    this.password = Objects.requireNonNull(password, "Password cannot be null.");
  }
}
