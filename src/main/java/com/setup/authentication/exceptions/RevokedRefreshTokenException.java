package com.setup.authentication.exceptions;

public class RevokedRefreshTokenException extends RuntimeException {
  public RevokedRefreshTokenException(String message) {
    super(message);
  }

  public RevokedRefreshTokenException() {
    super("Refresh token has been revoked");
  }
}
