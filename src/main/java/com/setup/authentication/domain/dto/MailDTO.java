package com.setup.authentication.domain.dto;

public record MailDTO(
        String from,
        String to,
        String subject,
        String body
) {
}
