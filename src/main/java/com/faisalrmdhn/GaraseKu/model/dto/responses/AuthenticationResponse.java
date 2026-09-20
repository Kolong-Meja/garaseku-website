package com.faisalrmdhn.GaraseKu.model.dto.responses;

public record AuthenticationResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    AuthenticatedUserResponse user) {
}
