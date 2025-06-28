package com.interviewprep.ai_interview_platform.service;

import com.interviewprep.ai_interview_platform.dto.RegisterRequest;
import com.interviewprep.ai_interview_platform.model.User;
import com.interviewprep.ai_interview_platform.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterRequest registerRequest) {
        // Check if username already exists
        if (userRepository.findByUsername(registerRequest.username()).isPresent()) {
            throw new IllegalStateException("Username already taken");
        }

        User newUser = new User();
        newUser.setUsername(registerRequest.username());
        newUser.setEmail(registerRequest.email());

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(registerRequest.password());
        newUser.setPassword(hashedPassword);

        // Save the new user to the database
        return userRepository.save(newUser);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalStateException("User not found with username: " + username));
    }
}
