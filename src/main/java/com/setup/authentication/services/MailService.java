package com.setup.authentication.services;

import com.setup.authentication.domain.dto.MailDTO;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String hostEmail;

    @Value("${spring.mail.confirmation.url}")
    private String confirmationUrl;

    @Value("${spring.mail.resetpassword.url}")
    private String resetPasswordUrl;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(MailDTO mailDto) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.to()); // user
        message.setSubject(mailDto.subject()); // title
        message.setText(mailDto.body()); // content
        message.setFrom(mailDto.from()); // me

        mailSender.send(message);
    }

    public void sendEmailConfirmation(String email, String token) throws MessagingException {

        MailDTO mail = new MailDTO(
                hostEmail,
                email,
                "Email Confirmation",
                "Please confirm your email by clicking the following link: " +
                        confirmationUrl + token
        );

        sendEmail(mail);

    }

    public void sendEmailResetPassword(String email, String token) throws MessagingException {

        MailDTO mail = new MailDTO(
                hostEmail,
                email,
                "Password Reset",
                "You can reset your password by clicking the following link: " +
                        resetPasswordUrl + token
        );

        sendEmail(mail);

    }
}
