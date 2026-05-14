package com.example.vehicalrentalserviceplatform.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingPageController {


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
    public String showBookVehicle(@RequestParam(name = "id",required = false)String vehicleId,
                                  HttpSession session, Model model) {

        String currentUser = (String) session.getAttribute("loggedInUser");

        if(currentUser == null){
            return "redirect:/login";
        }

        model.addAttribute("username", currentUser);
        model.addAttribute("vehicleId", vehicleId);


        return "bookVehicle";

    }

    @GetMapping("/editBooking")
    public String showEditBooking() {
        return "editBooking";
    }

}