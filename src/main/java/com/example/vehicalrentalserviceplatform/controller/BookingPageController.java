package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.model.Booking;
import com.example.vehicalrentalserviceplatform.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingPageController {

@Autowired
private BookingService bookingService;

    @GetMapping("/reservationHistory")
    public String showReservationHistory(HttpSession session, Model model) {
        String currentUser = (String) session.getAttribute("loggedInUser");

        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", currentUser);
        return "reservationHistory";
    }

    @GetMapping("/bookVehicle")
public String showBookVehicle(@RequestParam(name = "id", required = false) String vehicleId,
                              @RequestParam(name = "error", required = false) String error,
                              HttpSession session,
                              Model model) {

    String currentUser = (String) session.getAttribute("loggedInUser");

    if (currentUser == null) {
        return "redirect:/login";
    }

    if (vehicleId == null || vehicleId.isBlank()) {
        return "redirect:/catalog";
    }

    model.addAttribute("username", currentUser);
    model.addAttribute("vehicleId", vehicleId);
    model.addAttribute("error", error);

    return "bookVehicle";
}

   @GetMapping("/editBooking")
public String showEditBooking(@RequestParam(name = "id", required = false) String transactionId,
                              @RequestParam(name = "error", required = false) String error,
                              HttpSession session,
                              Model model) {

    String currentUser = (String) session.getAttribute("loggedInUser");

    if (currentUser == null) {
        return "redirect:/login";
    }

    if (transactionId == null || transactionId.isBlank()) {
        return "redirect:/reservationHistory";
    }

    Booking selectedBooking = null;

    for (Booking booking : bookingService.getAllBookings()) {
        if (booking.getTransactionId().equals(transactionId)) {
            selectedBooking = booking;
            break;
        }
    }

    if (selectedBooking == null) {
        return "redirect:/reservationHistory";
    }

    if (!selectedBooking.getCustomerName().equals(currentUser)) {
        return "redirect:/reservationHistory";
    }

    if (!selectedBooking.getBookingStatus().equalsIgnoreCase("Pending")) {
        return "redirect:/reservationHistory";
    }

    model.addAttribute("transactionId", selectedBooking.getTransactionId());
    model.addAttribute("vehicleId", selectedBooking.getVehicleId());
    model.addAttribute("startDate", selectedBooking.getStartDate());
    model.addAttribute("returnDate", selectedBooking.getReturnDate());
    model.addAttribute("error", error);

    return "editBooking";
}

}