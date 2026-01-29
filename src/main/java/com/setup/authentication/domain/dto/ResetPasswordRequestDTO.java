package com.setup.authentication.domain.dto;

public record ResetPasswordRequestDTO(
        String token,
        String newPassword
) {
}
