package com.faisalrmdhn.GaraseKu.model.dto.responses;

import java.util.Collection;
import java.util.Objects;

public record PaginationResponse<T>(
    Collection<T> data,
    int totalPages,
    long totalElements,
    int size,
    int page,
    boolean hasNext) {
  public PaginationResponse(
      Collection<T> data,
      int totalPages,
      long totalElements,
      int size,
      int page,
      boolean hasNext) {
    this.data = Objects.requireNonNull(data, "Data cannot be null.");
    this.totalPages = Objects.requireNonNull(totalPages, "Total pages cannot be null.");
    this.totalElements = Objects.requireNonNull(totalElements, "Total elements cannot be null.");
    this.size = Objects.requireNonNull(size, "Size cannot be null.");
    this.page = Objects.requireNonNull(page, "Page cannot be null.");
    this.hasNext = Objects.requireNonNull(hasNext, "Has next cannot be null.");
  }
}
