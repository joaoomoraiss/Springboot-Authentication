package com.setup.authentication.domain.dto;

public record TokenRequestDTO(
        String sub,
        String email,
        String type,
        String expiration
) {

}
