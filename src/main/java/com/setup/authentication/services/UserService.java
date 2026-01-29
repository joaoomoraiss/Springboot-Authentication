package com.setup.authentication.services;

import com.setup.authentication.domain.dto.RegisterRequestDTO;
import com.setup.authentication.domain.entities.Role;
import com.setup.authentication.domain.entities.User;
import com.setup.authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(RegisterRequestDTO registerRequestDTO) {

        User user = new User();
        LocalDateTime now = LocalDateTime.now();

        user.setEmail(registerRequestDTO.email());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
        user.setRole(Role.USER);
        user.setVerified(false);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return user;
    }

    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
