package com.setup.authentication.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDTO(

        String refreshToken
) {
}
