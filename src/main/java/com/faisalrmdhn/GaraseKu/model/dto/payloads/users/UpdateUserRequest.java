package com.faisalrmdhn.GaraseKu.model.dto.payloads.users;

import java.util.Objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(String username, String email, String phoneNumber, String fullname) {
  public UpdateUserRequest(
      @NotBlank(message = "Username cannot be blank") @Size(max = 100, message = "Username cannot exceed 100 characters") String username,
      @NotBlank(message = "Email cannot be blank") @Size(max = 100, message = "Email cannot exceed 100 characters") @Email(message = "Email should be valid and include @.") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.co\\\\.id$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email must end with .co.id") String email,
      @Size(max = 20, message = "Phone number cannot exceed 20 characters") @Pattern(regexp = "^(\\+62|62)?[\\s-]?0?8[1-9]{1}\\d{1}[\\s-]?\\d{4}[\\s-]?\\d{2,5}$", message = "Phone number does not comply with Indonesian country format standards.") String phoneNumber,
      @Size(max = 150, message = "Full name cannot exceed 150 characters") String fullname) {
    this.username = Objects.requireNonNull(username, "Username cannot be null");
    this.email = Objects.requireNonNull(email, "Email cannot be null");
    this.phoneNumber = Objects.requireNonNull(phoneNumber, "Phone number cannot be null");
    this.fullname = Objects.requireNonNull(fullname, "Full name cannot be null");
  }
}
