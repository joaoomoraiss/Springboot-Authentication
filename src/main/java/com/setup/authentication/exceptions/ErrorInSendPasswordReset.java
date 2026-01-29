package com.setup.authentication.exceptions;

public class ErrorInSendPasswordReset extends RuntimeException {
    public ErrorInSendPasswordReset(String message) {
        super(message);
    }

    public ErrorInSendPasswordReset() {
        super("Error sending password reset email");
    }
}
