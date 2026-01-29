package com.setup.authentication.controllers;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.setup.authentication.domain.dto.ErrorDTO;
import com.setup.authentication.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<ErrorDTO> handleEmailExistException(EmailExistException ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ErrorDTO> handleLoginFailedException(LoginFailedException ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(ErrorInSendEmailConfirmation.class)
    public ResponseEntity<ErrorDTO> handleErrorInSendEmailConfirmation(ErrorInSendEmailConfirmation ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    @ExceptionHandler(ErrorInSendPasswordReset.class)
    public ResponseEntity<ErrorDTO> handleErrorInSendPasswordReset(ErrorInSendPasswordReset ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    @ExceptionHandler(RefreshTokenExpired.class)
    public ResponseEntity<ErrorDTO> handleRefreshTokenExpired(RefreshTokenExpired ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(RevokedRefreshTokenException.class)
    public ResponseEntity<ErrorDTO> handleRevokedRefreshTokenException(RevokedRefreshTokenException ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorDTO> handleTokenExpiredException(TokenExpiredException ex) {
        ErrorDTO errorDTO = new ErrorDTO("Token has expired");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

}
