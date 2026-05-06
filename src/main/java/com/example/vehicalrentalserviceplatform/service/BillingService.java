package com.example.vehicalrentalserviceplatform.service;

import com.example.vehicalrentalserviceplatform.model.Invoice;
import com.example.vehicalrentalserviceplatform.model.InvoiceStatus;
import com.example.vehicalrentalserviceplatform.model.PaymentMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BillingService {

    private final ConcurrentMap<Long, Invoice> invoices = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public Invoice createInvoice(Invoice invoice) {
        validateInvoice(invoice);
        invoice.setId(nextId.getAndIncrement());
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setCreatedAt(invoice.getCreatedAt());
        invoice.getPaymentMethod().validate();
        invoices.put(invoice.getId(), invoice);
        return invoice;
    }

    public List<Invoice> getAllInvoices() {
        return Collections.unmodifiableList(new ArrayList<>(invoices.values()));
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return Optional.ofNullable(invoices.get(id));
    }

    public Invoice updateInvoice(Long id, Invoice update) {
        Invoice existing = invoices.get(id);
        if (existing == null) {
            throw new IllegalArgumentException("Invoice not found: " + id);
        }
        if (update.getDiscount() >= 0) {
            existing.applyDiscount(update.getDiscount());
        }
        if (update.getLateFee() >= 0) {
            existing.applyLateFee(update.getLateFee());
        }
        if (update.getStatus() != null) {
            existing.setStatus(update.getStatus());
            if (update.getStatus() == InvoiceStatus.PAID) {
                existing.markPaid();
            }
        }
        if (update.getPaymentMethod() != null) {
            update.getPaymentMethod().validate();
            existing.setPaymentMethod(update.getPaymentMethod());
        }
        validateInvoice(existing);
        invoices.put(id, existing);
        return existing;
    }

    public void deleteInvoice(Long id) {
        invoices.remove(id);
    }

    private void validateInvoice(Invoice invoice) {
        if (invoice.getCustomerName() == null || invoice.getCustomerName().isBlank()) {
            throw new IllegalArgumentException("Customer name is required.");
        }
        if (invoice.getVehicleName() == null || invoice.getVehicleName().isBlank()) {
            throw new IllegalArgumentException("Vehicle name is required.");
        }
        if (invoice.getRentalDays() <= 0) {
            throw new IllegalArgumentException("Rental days must be greater than zero.");
        }
        if (invoice.getAmountPerDay() <= 0) {
            throw new IllegalArgumentException("Amount per day must be greater than zero.");
        }
        if (invoice.getDiscount() < 0) {
            throw new IllegalArgumentException("Discount cannot be negative.");
        }
        if (invoice.getLateFee() < 0) {
            throw new IllegalArgumentException("Late fee cannot be negative.");
        }
        if (invoice.getPaymentMethod() == null) {
            throw new IllegalArgumentException("Payment method is required.");
        }
    }
}
