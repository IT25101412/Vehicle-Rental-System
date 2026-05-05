package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.model.Booking;
import com.example.vehicalrentalserviceplatform.service.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookingApiController {

    // 1. Tell the API controller about your service layer
    private final BookingService bookingService;

    // 2. Initialize the service
    public BookingApiController() {
        this.bookingService = new BookingService();
    }

    // 3. Your API endpoint for fetching JSON data
    @GetMapping("/api/bookings")
    public List<Booking> getBookings(@RequestParam(value = "customer", required = false) String customer) {

        // If the URL contains "?customer=Name", return only their bookings
        if (customer != null && !customer.isEmpty()) {
            return bookingService.getBookingsByCustomer(customer);
        }

        // If no customer is specified, act like an Admin and return everything
        return bookingService.getAllBookings();
    }
}