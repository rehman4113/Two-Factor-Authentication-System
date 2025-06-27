package com.TwoFactorAuthentication.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPVerificationRequest {
    @NotBlank(message = "Email is required")

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "OTP Code is required")
    private String otpCode;

    public @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @NotBlank(message = "OTP Code is required") String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(@NotBlank(message = "OTP Code is required") String otpCode) {
        this.otpCode = otpCode;
    }
}
