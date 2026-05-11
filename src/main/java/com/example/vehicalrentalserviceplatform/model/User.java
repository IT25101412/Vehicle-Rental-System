package com.example.vehicalrentalserviceplatform.model;

public class User {
    private String userId;
    private String fullName;
    private String username;
    private String role;

    public User(String userId, String fullName, String username, String role) {
        this.userId = userId;
        this.fullName = fullName;
        this.username = username;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
