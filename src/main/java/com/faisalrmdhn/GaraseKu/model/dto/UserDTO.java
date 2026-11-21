package com.faisalrmdhn.GaraseKu.model.dto;

import java.time.LocalDateTime;

public record UserDTO(String id,
    String username,
    String email,
    String phoneNumber,
    String fullname,
    String createdBy,
    LocalDateTime createdAt,
    String updatedBy,
    LocalDateTime updatedAt) {
  public UserDTO(String id,
      String username,
      String email,
      String phoneNumber,
      String fullname,
      String createdBy,
      LocalDateTime createdAt,
      String updatedBy,
      LocalDateTime updatedAt) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.fullname = fullname;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
    this.updatedBy = updatedBy;
    this.updatedAt = updatedAt;
  }
}
