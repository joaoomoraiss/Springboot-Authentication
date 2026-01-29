package com.setup.authentication.domain.dto;

import com.setup.authentication.domain.entities.Role;

import java.util.UUID;

public record RegisterResponseDTO(
        String email,
        Role role,
        String message
) {
}
