package com.faisalrmdhn.GaraseKu.model.dto.payloads.roles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateRoleRequest(String name, String desc) {
  public UpdateRoleRequest(
      @NotBlank(message = "Role name cannot be blank") @Size(max = 100, message = "Role name cannot exceed 100 characters") String name,
      @NotBlank(message = "Role description cannot be blank.") String desc) {
    this.name = name;
    this.desc = desc;
  }
}
