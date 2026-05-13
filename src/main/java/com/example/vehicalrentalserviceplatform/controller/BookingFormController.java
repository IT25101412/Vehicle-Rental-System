package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.model.Booking;
import com.example.vehicalrentalserviceplatform.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingFormController {

    private final BookingService bookingService;

    public BookingFormController() {
        this.bookingService = new BookingService();
    }

    // 1. Replaces your old BookingController (Create)
    @PostMapping("/createBooking")
    public String createBooking(
            @RequestParam("customerUsername") String name,
            @RequestParam("vehicleId") String vehicleId,
            @RequestParam("startDate") String startDate,
            @RequestParam("returnDate") String returnDate) {

        Booking newBooking = new Booking(name, vehicleId, startDate, returnDate, "Pending");
        bookingService.createBooking(newBooking);

        // Redirects the user to the checkout page with their new ID
        // Note: Make sure your PageController has a @GetMapping("/checkout.html") or just ("/checkout")
        return "redirect:/checkout.html?id=" + newBooking.getTransactionId();
    }

    // 2. Replaces your old UpdateBookingController
    @PostMapping("/updateBooking")
    public String updateBooking(
            @RequestParam("transactionId") String transactionId,
            @RequestParam("vehicleId") String newVehicleId,
            @RequestParam("startDate") String newStartDate,
            @RequestParam("returnDate") String newReturnDate) {

        String secureStatus = "Pending";
        for (Booking existing : bookingService.getAllBookings()) {
            if (existing.getTransactionId().equals(transactionId)) {
                secureStatus = existing.getBookingStatus();
                break;
            }
        }
        bookingService.updateBooking(transactionId, newVehicleId, newStartDate, newReturnDate, secureStatus);

        // Redirect back to the history page after saving
        return "redirect:/reservationHistory.html";
    }

    // 3. Replaces your old DeleteBookingController
    @PostMapping("/deleteBooking")
    public String deleteBooking(@RequestParam("transactionId") String targetID) {

        bookingService.deleteBooking(targetID);

        // Redirect back to the history page after deleting
        return "redirect:/reservationHistory.html";
    }

    @PostMapping("/admin/approveBooking")
    public String approveBooking(@RequestParam("transactionId") String transactionId) {
        bookingService.updateBookingStatus(transactionId, "Approved");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/rejectBooking")
    public String rejectBooking(@RequestParam("transactionId") String transactionId) {
        bookingService.updateBookingStatus(transactionId, "Rejected");
        return "redirect:/admin/dashboard";
    }


}