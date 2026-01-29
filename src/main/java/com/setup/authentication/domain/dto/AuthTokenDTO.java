package com.setup.authentication.domain.dto;

public record AuthTokenDTO(
        String accessToken,
        String refreshToken
) {
}
