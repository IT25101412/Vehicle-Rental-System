package com.example.vehicalrentalserviceplatform.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import jakarta.servlet.http.HttpSession;
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
        @RequestParam("vehicleId") String vehicleId,
        @RequestParam("startDate") String startDate,
        @RequestParam("returnDate") String returnDate,
        HttpSession session) {

    String currentUser = (String) session.getAttribute("loggedInUser");

    if (currentUser == null) {
        return "redirect:/login";
    }

    if (vehicleId == null || vehicleId.isBlank()) {
        return "redirect:/catalog";
    }

    try {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(returnDate);
        LocalDate today = LocalDate.now();

        if (start.isBefore(today)) {
            return "redirect:/bookVehicle?id=" + vehicleId + "&error=pastStartDate";
        }

        if (!end.isAfter(start)) {
            return "redirect:/bookVehicle?id=" + vehicleId + "&error=invalidReturnDate";
        }

    } catch (DateTimeParseException e) {
        return "redirect:/bookVehicle?id=" + vehicleId + "&error=invalidDateFormat";
    }

    Booking newBooking = new Booking(currentUser, vehicleId, startDate, returnDate, "Pending");
boolean created = bookingService.createBooking(newBooking);

if (!created) {
    return "redirect:/bookVehicle?id=" + vehicleId + "&error=vehicleBooked";
}

return "redirect:/reservationHistory";
}

    // 2. Replaces your old UpdateBookingController
    @PostMapping("/updateBooking")
public String updateBooking(
        @RequestParam("transactionId") String transactionId,
        @RequestParam("startDate") String newStartDate,
        @RequestParam("returnDate") String newReturnDate,
        HttpSession session) {

    String currentUser = (String) session.getAttribute("loggedInUser");

    if (currentUser == null) {
        return "redirect:/login";
    }

    Booking selectedBooking = null;

    for (Booking existing : bookingService.getAllBookings()) {
        if (existing.getTransactionId().equals(transactionId)) {
            selectedBooking = existing;
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

    try {
        LocalDate start = LocalDate.parse(newStartDate);
        LocalDate end = LocalDate.parse(newReturnDate);
        LocalDate today = LocalDate.now();

        if (start.isBefore(today)) {
            return "redirect:/editBooking?id=" + transactionId + "&error=pastStartDate";
        }

        if (!end.isAfter(start)) {
            return "redirect:/editBooking?id=" + transactionId + "&error=invalidReturnDate";
        }

    } catch (DateTimeParseException e) {
        return "redirect:/editBooking?id=" + transactionId + "&error=invalidDateFormat";
    }

    bookingService.updateBooking(
            transactionId,
            selectedBooking.getVehicleId(),
            newStartDate,
            newReturnDate,
            selectedBooking.getBookingStatus()
    );

    return "redirect:/reservationHistory";
}

    // 3. Replaces your old DeleteBookingController
    @PostMapping("/deleteBooking")
public String deleteBooking(@RequestParam("transactionId") String transactionId,
                            HttpSession session) {

    String currentUser = (String) session.getAttribute("loggedInUser");

    if (currentUser == null) {
        return "redirect:/login";
    }

    Booking selectedBooking = null;

    for (Booking existing : bookingService.getAllBookings()) {
        if (existing.getTransactionId().equals(transactionId)) {
            selectedBooking = existing;
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

    bookingService.deleteBooking(transactionId);

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