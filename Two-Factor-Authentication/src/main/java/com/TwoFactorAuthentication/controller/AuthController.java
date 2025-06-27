package com.TwoFactorAuthentication.controller;

import com.TwoFactorAuthentication.dtos.LoginRequest;
import com.TwoFactorAuthentication.dtos.SignUpRequest;
import com.TwoFactorAuthentication.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Allow requests from your frontend
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        return authService.signUp(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}