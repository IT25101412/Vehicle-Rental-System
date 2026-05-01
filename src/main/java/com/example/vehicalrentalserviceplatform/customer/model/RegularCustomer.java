package com.example.vehicalrentalserviceplatform.customer.model;

// Customer class — extends User (Inheritance)
public class RegularCustomer extends User {

    // Private fields — Encapsulation
    private String email;
    private String phone;

    // Constructor — super() sets the parent fields
    public RegularCustomer(String username, String password, String licenseId,
                           String email, String phone) {
        super(username, password, licenseId);
        this.email = email;
        this.phone = phone;
    }

    // Default constructor — needed for Spring form binding
    public RegularCustomer() {
        super();
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Converts object to one line for saving in customer.txt
    public String toFileString() {
        return "CUSTOMER," +
                getUsername() + "," +
                getPassword() + "," +
                getLicenseId() + "," +
                email + "," +
                phone;
    }

    @Override
    public String toString() {
        return "RegularCustomer{" +
                "username='" + getUsername() + '\'' +
                ", licenseId='" + getLicenseId() + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}