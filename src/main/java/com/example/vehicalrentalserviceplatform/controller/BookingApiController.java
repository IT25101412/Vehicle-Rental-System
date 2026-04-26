package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.model.Booking;
import com.example.vehicalrentalserviceplatform.service.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class BookingApiController {

    // This creates a URL endpoint that outputs pure data instead of a web page
    @GetMapping("/api/bookings")
    public List<Booking> getBookingsForJavaScript() {
        BookingService service = new BookingService();

        // Spring Boot automatically converts this Java List into a JSON string!
        return service.getAllBookings();
    }
}