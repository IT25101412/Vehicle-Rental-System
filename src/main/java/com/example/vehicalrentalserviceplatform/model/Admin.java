package com.example.vehicalrentalserviceplatform.model;

public class Admin extends User {
    private String employeeType;
    private String passwordHash;

    public Admin(String userId, String fullName, String username, String role, String employeeType, String passwordHash) {
        super(userId, fullName, username, null, null, role);
        this.employeeType = employeeType;
        this.passwordHash = passwordHash;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public String getMaskedPasswordHash() {
        if (passwordHash == null || passwordHash.isEmpty()) {
            return "****";
        }
        if (passwordHash.length() <= 4) {
            return passwordHash + "****";
        }
        return passwordHash.substring(0, 4) + "******";
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String toFileRecord() {
        return getUserId() + "," + getFullName() + "," + getUsername() + "," + getRole() + "," + employeeType + "," + passwordHash;
    }
}
