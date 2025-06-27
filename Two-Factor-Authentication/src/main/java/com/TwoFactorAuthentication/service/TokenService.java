package com.TwoFactorAuthentication.service;

import com.TwoFactorAuthentication.models.Token;
import com.TwoFactorAuthentication.models.User;
import com.TwoFactorAuthentication.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    // Save new token
    public void saveToken(User user, String tokenValue) {
        Token token = new Token(tokenValue, user.getId(), false);
        tokenRepository.save(token);
    }

    // Validate Token
    public boolean isTokenValid(String tokenValue) {
        Optional<Token> tokenOptional = tokenRepository.findByTokenValue(tokenValue);  // Corrected method name
        return tokenOptional.isPresent() && !tokenOptional.get().isRevoked();
    }

    // Revoke Token (Logout)
    public void revokeToken(String tokenValue) {
        Optional<Token> tokenOptional = tokenRepository.findByTokenValue(tokenValue);  // Corrected method name
        tokenOptional.ifPresent(token -> {
            token.setRevoked(true);
            tokenRepository.save(token);
        });
    }
}
