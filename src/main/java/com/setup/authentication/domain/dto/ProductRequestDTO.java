package com.setup.authentication.domain.dto;

import java.math.BigDecimal;

public record ProductRequestDTO(
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) {
}
