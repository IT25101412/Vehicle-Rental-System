package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.model.Invoice;
import com.example.vehicalrentalserviceplatform.service.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping
    public List<Invoice> allInvoices() {
        return billingService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id) {
        return billingService.getInvoiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice) {
        try {
            Invoice created = billingService.createInvoice(invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        try {
            Invoice updated = billingService.updateInvoice(id, invoice);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        billingService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
