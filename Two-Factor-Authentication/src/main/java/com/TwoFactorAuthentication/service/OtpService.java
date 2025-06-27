package com.TwoFactorAuthentication.service;

import com.TwoFactorAuthentication.dtos.OTPRequest;
import com.TwoFactorAuthentication.models.OTP;
import com.TwoFactorAuthentication.repository.OtpRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {
    private final OtpRepository otpRepository;
    private final EmailService emailService;

    public OtpService(OtpRepository otpRepository, EmailService emailService) {
        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }

    // Generate and send OTP via email
    public void sendOtpToUser(String email) {
        String otpCode = generateOtp(); // Generate OTP

        // Save OTP in database
        OTP otp = new OTP(email, otpCode);
        otpRepository.save(otp);

        // Send OTP to email
        emailService.sendOtpEmail(email, otpCode);

        // Log OTP (for debugging)
        System.out.println("OTP sent to: " + email + " | OTP: " + otpCode);
    }

    // Generate a 6-digit OTP
    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    // Verify OTP
    public boolean verifyOtp(String email, String otpCode) {
        Optional<OTP> otpOptional = otpRepository.findByEmail(email);
        if (otpOptional.isPresent()) {
            OTP otp = otpOptional.get();
            if (otp.getOtpCode().equals(otpCode)) {
                otpRepository.delete(otp); // OTP is used, so remove it
                return true;
            }
        }
        return false;
    }

    // Resend OTP
    public void resendOtp(OTPRequest request) {
        sendOtpToUser(request.getEmail());
    }
}
