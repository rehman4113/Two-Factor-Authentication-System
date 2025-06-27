package com.TwoFactorAuthentication.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "users")  // Ensure correct collection name
public class User {
    @Id
    private String id;  // MongoDB uses String _id

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private LocalDateTime createdAt;

    public User(String firstName, String lastName, String email, String password, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.createdAt = LocalDateTime.now(); // Auto-set timestamp
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
