package com.example.vehicalrentalserviceplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingPageController {


    @GetMapping("/catalog")
    public String showCatalog() {
        return "catalog";
    }

    @GetMapping({"/checkout", "/checkout.html"})
    public String showCheckout() {
        return "checkout";
    }


    @GetMapping("/reservationHistory.html")
    public String showReservationHistory() {
        return "reservationHistory";
    }

    @GetMapping("/bookVehicle.html")
    public String showBookVehicle() {
        return "bookVehicle";
    }

    @GetMapping("/editBooking.html")
    public String showEditBooking() {
        return "editBooking";
    }

}