package com.interviewprep.ai_interview_platform.controller;

import com.interviewprep.ai_interview_platform.dto.LoginRequest;
import com.interviewprep.ai_interview_platform.dto.LoginResponse;
import com.interviewprep.ai_interview_platform.dto.RegisterRequest;
import com.interviewprep.ai_interview_platform.model.User;
import com.interviewprep.ai_interview_platform.security.JwtTokenProvider;
import com.interviewprep.ai_interview_platform.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthController(UserService userService, 
                         AuthenticationManager authenticationManager,
                         JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            User newUser = userService.registerUser(registerRequest);
            return ResponseEntity.ok("User registered successfully! ID: " + newUser.getId());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
            );

            // Set authentication in context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String jwt = tokenProvider.generateToken(authentication);

            // Get user details
            User user = userService.getUserByUsername(loginRequest.username());

            // Create and return response with token
            LoginResponse response = new LoginResponse(
                jwt,
                user.getUsername(),
                user.getEmail()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }
}
