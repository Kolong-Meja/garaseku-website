package com.faisalrmdhn.GaraseKu.model.dto.payloads;

public record PaginationRequest(Integer page, Integer size, String direction, String sortField) {
  public PaginationRequest(Integer page, Integer size, String direction, String sortField) {
    this.page = page;
    this.size = size;
    this.direction = direction;
    this.sortField = sortField;
  }
}
