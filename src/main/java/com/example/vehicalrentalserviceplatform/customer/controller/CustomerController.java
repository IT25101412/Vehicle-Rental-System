package com.example.vehicalrentalserviceplatform.customer.controller;

import com.example.vehicalrentalserviceplatform.customer.model.RegularCustomer;
import com.example.vehicalrentalserviceplatform.customer.model.User;
import com.example.vehicalrentalserviceplatform.customer.service.CustomerFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Handles all customer page requests
@Controller
public class CustomerController {

    // Spring automatically injects the service
    @Autowired
    private CustomerFileService customerFileService;

    // REGISTER

    // Shows the registration page
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("customer", new RegularCustomer());
        return "register";
    }

    // Handles registration form submission
    @PostMapping("/register")
    public String handleRegister(@ModelAttribute RegularCustomer customer, Model model) {
        boolean success = customerFileService.registerCustomer(customer);
        if (success) {
            model.addAttribute("message", "Registration successful! Please login.");
            return "login";
        } else {
            model.addAttribute("error", "Username already exists. Please choose another.");
            return "register";
        }
    }

    // LOGIN

    // Shows the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Handles login form submission
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              Model model) {
        boolean valid = customerFileService.loginCustomer(username, password);
        if (valid) {
            // Find customer and send to profile page
            User customer = customerFileService.findCustomer(username);
            model.addAttribute("customer", customer);
            return "profile";
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }

    // PROFILE

    // Shows the profile page for a specific customer
    @GetMapping("/profile")
    public String showProfilePage(@RequestParam String username, Model model) {
        User customer = customerFileService.findCustomer(username);
        if (customer == null) {
            model.addAttribute("error", "Customer not found.");
            return "login";
        }
        model.addAttribute("customer", customer);
        return "profile";
    }

    // Handles profile update form submission
    @PostMapping("/profile/update")
    public String handleUpdate(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String phone,
                               @RequestParam String address,
                               Model model) {
        boolean updated = customerFileService.updateCustomer(username, email, phone, address);
        if (updated) {
            // Reload updated customer and show profile
            User customer = customerFileService.findCustomer(username);
            model.addAttribute("customer", customer);
            model.addAttribute("message", "Profile updated successfully.");
        } else {
            model.addAttribute("error", "Update failed. Customer not found.");
        }
        return "profile";
    }
}