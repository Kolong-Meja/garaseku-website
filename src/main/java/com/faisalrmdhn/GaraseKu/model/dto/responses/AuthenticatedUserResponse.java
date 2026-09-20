package com.faisalrmdhn.GaraseKu.model.dto.responses;

import java.util.Map;
import java.util.Set;

public record AuthenticatedUserResponse(
    String userId,
    String username,
    String email,
    String phoneNumber,
    String fullname,
    Set<String> roles,
    Map<String, String> settings) {
}
