package com.example.vehicalrentalserviceplatform.customer.model;

// Regular customer class — extends User (Inheritance)
public class RegularCustomer extends User {

    // Private fields — Encapsulation
    private String email;
    private String phone;
    private String address;

    // Constructor — super() sets the parent fields
    public RegularCustomer(String username, String password, String licenseId,
                           String email, String phone, String address) {
        super(username, password, licenseId);
        this.email = email;
        this.phone = phone;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Converts object to one line for saving in customer.txt
    public String toFileString() {
        return "REGULAR," +
                getUsername() + "," +
                getPassword() + "," +
                getLicenseId() + "," +
                email + "," +
                phone + "," +
                address;
    }

    @Override
    public String toString() {
        return "RegularCustomer{" +
                "username='" + getUsername() + '\'' +
                ", licenseId='" + getLicenseId() + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}