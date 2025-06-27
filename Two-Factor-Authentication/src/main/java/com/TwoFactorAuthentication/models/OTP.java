package com.TwoFactorAuthentication.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "otp_codes") // Defines the MongoDB collection name
public class OTP {
    @Id
    private String id;
    private String otpCode;
    private LocalDateTime expirationTime;
    private String email; // Add this field


    public OTP(String email, String otpCode) {
        this.email = email;
        this.otpCode = otpCode;
        this.expirationTime = LocalDateTime.now().plusDays(2);
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
