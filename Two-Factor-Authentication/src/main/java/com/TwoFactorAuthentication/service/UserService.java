package com.TwoFactorAuthentication.service;

import com.TwoFactorAuthentication.models.User;
import com.TwoFactorAuthentication.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public ResponseEntity<?> deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("User not found!");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
