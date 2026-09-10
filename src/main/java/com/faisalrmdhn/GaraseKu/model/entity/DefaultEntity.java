package com.faisalrmdhn.GaraseKu.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

public class DefaultEntity {
  @Column(name = "created_by", nullable = true)
  private String createdBy;

  @Column(name = "created_at", nullable = true)
  private LocalDateTime createdAt;

  @Column(name = "updated_by", nullable = true)
  private String updatedBy;

  @Column(name = "updated_at", nullable = true)
  private LocalDateTime updatedAt;

  public DefaultEntity() {
  }

  public DefaultEntity(String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt) {
    this.createdBy = createdBy;
    this.createdAt = createdAt;
    this.updatedBy = updatedBy;
    this.updatedAt = updatedAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

}
