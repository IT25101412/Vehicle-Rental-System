package com.example.vehicalrentalserviceplatform.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingPageController {


    @GetMapping("/reservationHistory.html")
    public String showReservationHistory(HttpSession session, Model model) {
        String currentUser = (String) session.getAttribute("loggedInUser");

        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", currentUser);
        return "reservationHistory";
    }

    @GetMapping("/bookVehicle.html")
    public String showBookVehicle(HttpSession session, Model model) {

        String currentUser = (String) session.getAttribute("loggedInUser");

        if(currentUser == null){
            return "redirect:/login";
        }

        model.addAttribute("username", currentUser);
        return "bookVehicle";


    }

    @GetMapping("/editBooking.html")
    public String showEditBooking() {
        return "editBooking";
    }

}