package com.example.vehicalrentalserviceplatform.customer.model;

// Single unified customer class — no Regular/Premium split
// All customer data lives here with full Encapsulation
public class User {

    // Private fields — Encapsulation
    private String username;
    private String password;
    private String licenseId;
    private String email;
    private String phone;

    // Full constructor
    public User(String username, String password, String licenseId,
                String email, String phone) {
        this.username = username;
        this.password = password;
        this.licenseId = licenseId;
        this.email = email;
        this.phone = phone;
    }

    // Default constructor — needed for Spring form binding
    public User() {}

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getLicenseId() { return licenseId; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setLicenseId(String licenseId) { this.licenseId = licenseId; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    // Validates login password
    public boolean validatePassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }

    // Converts object to one comma-separated line for saving in customer.txt
    public String toFileString() {
        return username + "," + password + "," + licenseId + "," + email + "," + phone;
    }

    @Override
    public String toString() {
        return "User{username='" + username + "', licenseId='" + licenseId +
                "', email='" + email + "', phone='" + phone + "'}";
    }
}