package com.setup.authentication.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
