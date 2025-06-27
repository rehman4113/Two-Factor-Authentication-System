package com.TwoFactorAuthentication.repository;

import com.TwoFactorAuthentication.models.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
