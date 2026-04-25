package com.example.vehicalrentalserviceplatform.model;

import java.time.LocalDateTime;

public class Invoice {

    private Long id;
    private String customerName;
    private String vehicleName;
    private int rentalDays;
    private double amountPerDay;
    private double discount;
    private double lateFee;
    private InvoiceStatus status;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;

    public Invoice() {
        this.status = InvoiceStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public double getAmountPerDay() {
        return amountPerDay;
    }

    public void setAmountPerDay(double amountPerDay) {
        this.amountPerDay = amountPerDay;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getLateFee() {
        return lateFee;
    }

    public void setLateFee(double lateFee) {
        this.lateFee = lateFee;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public double getSubtotal() {
        return rentalDays * amountPerDay;
    }

    public double getTotalAmount() {
        double total = getSubtotal() - discount + lateFee;
        return Math.max(total, 0.0);
    }

    public void applyDiscount(double discountAmount) {
        this.discount = discountAmount;
    }

    public void applyLateFee(double lateFeeAmount) {
        this.lateFee = lateFeeAmount;
    }

    public void markPaid() {
        this.status = InvoiceStatus.PAID;
        this.paidAt = LocalDateTime.now();
    }
}
