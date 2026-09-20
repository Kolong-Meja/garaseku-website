package com.faisalrmdhn.GaraseKu.model.dto.payloads;

import java.util.Objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    String username,
    String email,
    String phoneNumber,
    String password,
    String fullname) {
  public RegisterRequest(
      @NotBlank(message = "Username cannot be blank.") @Pattern(regexp = "^[a-z0-9_-]{5,100}$") @Size(max = 100, message = "Username only has a maximum of 100 characters.") String username,

      @NotBlank(message = "Email cannot be blank.") @Size(max = 100, message = "Email only has a maximum of 100 characters.") @Email(message = "Email should be valid and include @.") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.co\\\\.id$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email must end with .co.id") String email,

      @NotBlank(message = "Phone Number cannot be blank.") @Size(max = 20, message = "Phone Number only has a maximum of 20 characters.") @Pattern(regexp = "^(\\+62|62)?[\\s-]?0?8[1-9]{1}\\d{1}[\\s-]?\\d{4}[\\s-]?\\d{2,5}$", message = "Phone number does not comply with Indonesian country format standards.") String phoneNumber,

      @NotBlank(message = "Password cannot be blank.") @Size(min = 8, max = 100, message = "Password must have at least 8 characters.") @Pattern(regexp = "^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must include uppercase, lowercase, and a number.") String password,

      @NotBlank(message = "Fullname cannot be blank.") @Size(max = 150, message = "Fullname only has a maximum of 150 characters.") String fullname) {
    this.username = Objects.requireNonNull(username, "Username cannot be null.");
    this.email = Objects.requireNonNull(email, "Email cannot be null.");
    this.phoneNumber = Objects.requireNonNull(phoneNumber, "Phone Number cannot be null.");
    this.password = Objects.requireNonNull(password, "Password cannot be null.");
    this.fullname = Objects.requireNonNull(fullname, "Fullname cannot be null.");
  }
}
