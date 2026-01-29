package com.setup.authentication.services;

import com.setup.authentication.domain.dto.*;
import com.setup.authentication.domain.entities.User;
import com.setup.authentication.exceptions.EmailExistException;
import com.setup.authentication.exceptions.ErrorInSendEmailConfirmation;
import com.setup.authentication.exceptions.LoginFailedException;
import com.setup.authentication.repositories.UserRepository;
import com.setup.authentication.security.TokenService;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final MailService mailService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository, UserService userService, AuthenticationManager authenticationManager, TokenService tokenService, MailService mailService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.mailService = mailService;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO request) {

        // Check if user already exists
        if (userRepository.findByEmail(request.email()) != null) {
            // TO-DO: custom exception
            throw new EmailExistException("Email already registered");
        }

        // Create new user
        User newUser = userService.createUser(request);
        User savedUser = userService.saveUser(newUser);

        // Generate email confirmation token
        String confirmationToken = tokenService.generateEmailConfirmationToken(savedUser.getEmail());

        // Send confirmation email
        try {
            mailService.sendEmailConfirmation(savedUser.getEmail(), confirmationToken);
        } catch (Exception e) {
            throw new ErrorInSendEmailConfirmation("Error sending confirmation email" + e);
        }

        return new RegisterResponseDTO(
                savedUser.getEmail(),
                savedUser.getRole(),
                "User registered successfully. Please check your email to confirm your account."
        );
    }

    public AuthTokenDTO login(LoginRequestDTO request) {
        var user = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = authenticationManager.authenticate(user);

        if (auth.getPrincipal() == null) {
            throw new LoginFailedException("Authentication failed");
        }

        User authenticatedUser = (User) auth.getPrincipal();
        String accessToken = tokenService.generateToken(authenticatedUser);

        // create and save refresh token
        String refreshToken = refreshTokenService.createRefreshToken(authenticatedUser);

        return new AuthTokenDTO(accessToken, refreshToken);
    }

    public void confirmEmail(String token) {
        //validate token
        TokenRequestDTO tokenRequest = tokenService.validateEmailConfirmationToken(token);

        //get user
        User user = (User) userRepository.findByEmail(tokenRequest.email());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (user.isVerified()) return;

        //confirm email
        user.setVerified(true);
        userRepository.save(user);
    }

    public void sendEmailConfirmation(String email) {

        // generate token
        String emailConfirmationToken = tokenService.generateEmailConfirmationToken(email);

        //send email
        try {
            mailService.sendEmailConfirmation(email, emailConfirmationToken);
        } catch (MessagingException e) {
            throw new ErrorInSendEmailConfirmation("Error sending email confirmation " + e);
        }
    }

    public void sendResetPassword(String email) {

        //get token
        String resetPasswordToken = tokenService.generateResetPasswordToken(email);

        //send email
        try {
            mailService.sendEmailResetPassword(email, resetPasswordToken);
        } catch (MessagingException e) {
            throw new ErrorInSendEmailConfirmation("Error sending reset password " + e);
        }
    }

    public void resetPassword(ResetPasswordRequestDTO request) {

        //validate token
        TokenRequestDTO tokenData = tokenService.validateResetPasswordToken(request.token());

        //get user
        User user = (User) userRepository.findByEmail(tokenData.email());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        //reset password
        userService.updatePassword(user, request.newPassword());

    }

    @Transactional
    public AuthTokenDTO refreshToken(String refreshToken) {

        var refreshTokenEntity = refreshTokenService.validateRefreshToken(refreshToken);

        User user = refreshTokenEntity.getUser();

        // revoke old refresh token
        refreshTokenService.revokeToken(refreshToken);

        // Ggenerate new tokens
        String newAccessToken = tokenService.generateToken(user);
        String newRefreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthTokenDTO(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void logout(RefreshTokenRequestDTO request) {
        refreshTokenService.revokeToken(request.refreshToken());
    }

}
