package com.example.vehicalrentalserviceplatform.billing.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public String processPayment(Double amount , String method){

        return "payment of " + amount + " via " + method + " successful";

    }
}
