package com.TwoFactorAuthentication.service;

import com.TwoFactorAuthentication.dtos.LoginRequest;
import com.TwoFactorAuthentication.dtos.SignUpRequest;
import com.TwoFactorAuthentication.models.User;
import com.TwoFactorAuthentication.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final OtpService otpService;

    public AuthService(UserRepository userRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    // User Signup
    public ResponseEntity<?> signUp(SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }

        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhone()
        );

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // Login & Send OTP
    public ResponseEntity<?> login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty() || !request.getPassword().equals(userOptional.get().getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials!");
        }

        User user = userOptional.get();

        // Generate and send OTP
        try {
            otpService.sendOtpToUser(user.getEmail()); // Sends OTP via email
            return ResponseEntity.ok("OTP sent to your email. Please verify.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send OTP: " + e.getMessage());
        }
    }

    // Verify OTP
    public ResponseEntity<?> verifyOtp(String email, String otpCode) {
        if (!otpService.verifyOtp(email, otpCode)) {
            return ResponseEntity.badRequest().body("Invalid OTP! Please request a new one.");
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found!");
        }

        return ResponseEntity.ok("Login successful!");
    }
}
