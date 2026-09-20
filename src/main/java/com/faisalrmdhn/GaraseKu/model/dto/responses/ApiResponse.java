package com.faisalrmdhn.GaraseKu.model.dto.responses;

import java.util.Objects;

public record ApiResponse(
    int status,
    boolean success,
    String message,
    String timestamps,
    Object resource) {
  public ApiResponse(
      int status,
      boolean success,
      String message,
      String timestamps,
      Object resource) {
    this.status = Objects.requireNonNull(status, "Status cannot be null.");
    this.success = success;
    this.message = Objects.requireNonNull(message, "Message cannot be null.");
    this.timestamps = Objects.requireNonNull(timestamps, "Timestamps cannot be null.");
    this.resource = Objects.requireNonNull(resource, "Resource cannot be null.");
  }
}
