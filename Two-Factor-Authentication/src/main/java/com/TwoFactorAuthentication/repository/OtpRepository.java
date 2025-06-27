package com.TwoFactorAuthentication.repository;

import com.TwoFactorAuthentication.models.OTP;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OtpRepository extends MongoRepository<OTP, String> {
    Optional<OTP> findByEmail(String email); // Find OTP by user email
    void deleteByEmail(String email); // Delete OTP by email for cleanup
}
