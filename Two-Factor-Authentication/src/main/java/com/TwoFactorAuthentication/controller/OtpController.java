package com.TwoFactorAuthentication.controller;

import com.TwoFactorAuthentication.dtos.OTPRequest;
import com.TwoFactorAuthentication.service.AuthService;
import com.TwoFactorAuthentication.service.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class OtpController {

    private final OtpService otpService;

    private final AuthService authService;

    public OtpController(OtpService otpService, AuthService authService) {
        this.otpService = otpService;
        this.authService = authService;
    }

    // Send OTP when user enters email
    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestBody OTPRequest request) {
        otpService.sendOtpToUser(request.getEmail());
        return ResponseEntity.ok("OTP has been sent to " + request.getEmail());
    }

    // Resend OTP
    @PostMapping("/resend")
    public ResponseEntity<String> resendOtp(@RequestBody OTPRequest request) {
        otpService.resendOtp(request);
        return ResponseEntity.ok("New OTP has been sent to " + request.getEmail());
    }

    // Verify OTP
    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody OTPRequest request) {
        return authService.verifyOtp(request.getEmail(), request.getOtp());
    }

//    @PostMapping("/verify")
//    public ResponseEntity<String> verifyOtp(@RequestBody OTPRequest request) {
//        boolean isValid = otpService.verifyOtp(request.getEmail(), request.getOtp());
//
//        if (isValid) {
//            return ResponseEntity.ok("OTP verified successfully.");
//        } else {
//            return ResponseEntity.badRequest().body("Invalid or expired OTP. Please request a new one.");
//        }
//    }
}
