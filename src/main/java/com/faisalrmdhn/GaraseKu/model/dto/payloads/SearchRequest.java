package com.faisalrmdhn.GaraseKu.model.dto.payloads;

import java.util.Objects;

public record SearchRequest(String query) {
  public SearchRequest(String query) {
    this.query = Objects.requireNonNull(query, "Query cannot be null.");
  }
}
