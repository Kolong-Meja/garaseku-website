package com.faisalrmdhn.GaraseKu.model.dto.payloads;

public record PaginationRequest(int page, int size, String direction, String sortField) {
  public PaginationRequest(int page, int size, String direction, String sortField) {
    this.page = page;
    this.size = size;
    this.direction = direction;
    this.sortField = sortField;
  }
}
