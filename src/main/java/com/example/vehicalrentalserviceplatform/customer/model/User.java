package com.example.vehicalrentalserviceplatform.customer.model;


public class User {

    // Private fields — Encapsulation
    private String username;
    private String password;
    private String licenseId;

    // Parameterised constructor
    public User(String username, String password, String licenseId) {
        this.username = username;
        this.password = password;
        this.licenseId = licenseId;
    }

    // Default constructor
    public User() {}

    // Getters

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLicenseId() {
        return licenseId;
    }

    // Setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    // Utility


    public boolean validatePassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", licenseId='" + licenseId + '\'' +
                '}';
    }
}