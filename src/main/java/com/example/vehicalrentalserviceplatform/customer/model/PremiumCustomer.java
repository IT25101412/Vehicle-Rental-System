package com.example.vehicalrentalserviceplatform.customer.model;

// Premium customer class — extends User (Inheritance)
public class PremiumCustomer extends User {

    // Private fields — Encapsulation
    private String email;
    private String phone;
    private String address;
    private String membershipLevel; // SILVER, GOLD, or PLATINUM

    // Constructor — super() sets the parent fields
    public PremiumCustomer(String username, String password, String licenseId,
                           String email, String phone, String address,
                           String membershipLevel) {
        super(username, password, licenseId);
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.membershipLevel = membershipLevel;
    }

    // Default constructor — needed for Spring form binding
    public PremiumCustomer() {
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

    public String getMembershipLevel() {
        return membershipLevel;
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

    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    // Converts object to one line for saving in customer.txt
    public String toFileString() {
        return "PREMIUM," +
                getUsername() + "," +
                getPassword() + "," +
                getLicenseId() + "," +
                email + "," +
                phone + "," +
                address + "," +
                membershipLevel;
    }

    @Override
    public String toString() {
        return "PremiumCustomer{" +
                "username='" + getUsername() + '\'' +
                ", licenseId='" + getLicenseId() + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", membershipLevel='" + membershipLevel + '\'' +
                '}';
    }
}