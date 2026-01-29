package com.setup.authentication.domain.dto;

public record LoginRequestDTO(
        String email,
        String password
) {
}
