package com.setup.authentication.exceptions;

public class ErrorInSendEmailConfirmation extends RuntimeException {
    public ErrorInSendEmailConfirmation(String message) {
        super(message);
    }

    public ErrorInSendEmailConfirmation() {
        super("Error sending email confirmation");
    }
}
