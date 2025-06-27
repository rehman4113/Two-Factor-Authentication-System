package com.TwoFactorAuthentication.repository;

import com.TwoFactorAuthentication.models.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token,String> {

    Optional<Token> findByTokenValue(String tokenValue); // Ensure method name matches field

}
