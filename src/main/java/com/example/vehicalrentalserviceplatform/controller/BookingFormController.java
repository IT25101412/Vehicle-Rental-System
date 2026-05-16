package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.model.Booking;
import com.example.vehicalrentalserviceplatform.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookingFormController {

    @Autowired
    private BookingService bookingService;

    // 1. Replaces your old BookingController (Create)
    @PostMapping("/createBooking")
    public String createBooking(
            @RequestParam("customerUsername") String name,
            @RequestParam("vehicleId") String vehicleId,
            @RequestParam("startDate") String startDate,
            @RequestParam("returnDate") String returnDate) {

        Booking newBooking = new Booking(name, vehicleId, startDate, returnDate, "Pending");
        boolean isSuccess =  bookingService.createBooking(newBooking);

        if(!isSuccess){
            return "redirect:/bookVehicle?id=" + vehicleId + "&error=overlap";
        }

        return "redirect:/reservationHistory";
    }

    // 2. Replaces your old UpdateBookingController
    @PostMapping("/updateBooking")
    public String updateBooking(
            @RequestParam("transactionId") String transactionId,
            @RequestParam("vehicleId") String newVehicleId,
            @RequestParam("startDate") String newStartDate,
            @RequestParam("returnDate") String newReturnDate) {

        Booking existing = bookingService.getBookingById(transactionId);
        String secureStatus = (existing != null) ? existing.getBookingStatus() : "Pending";

        boolean isSuccess = bookingService.updateBooking(transactionId,newVehicleId,newStartDate,newReturnDate,secureStatus);

        if(!isSuccess){
            return "redirect:/editBooking?id=" + transactionId + "&vehicle=" + newVehicleId + "&start=" + newStartDate + "&end=" + newReturnDate + "&error=overlap";
        }
        return "redirect:/reservationHistory";
    }

    // 3. Replaces your old DeleteBookingController
    @PostMapping("/deleteBooking")
    public String deleteBooking(@RequestParam("transactionId") String targetID) {

        bookingService.deleteBooking(targetID);

        // Redirect back to the history page after deleting
        return "redirect:/reservationHistory";
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

    @PostMapping("/markAsPaid")
    @ResponseBody // This tells Spring Boot not to look for an HTML file, just return a success string
    public String markAsPaid(@RequestParam("transactionId") String transactionId) {
        if (transactionId != null && !transactionId.isEmpty()) {
            bookingService.updateBookingStatus(transactionId, "Paid");
        }
        return "Success";
    }


}