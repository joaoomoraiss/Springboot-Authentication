package com.setup.authentication.exceptions;

public class RefreshTokenExpired extends RuntimeException {
    public RefreshTokenExpired(String message) {
        super(message);
    }

    public RefreshTokenExpired() {
        super("Refresh token has expired");
    }
}
