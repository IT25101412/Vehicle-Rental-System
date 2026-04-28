package com.example.vehicalrentalserviceplatform.customer.controller;

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

    @Autowired
    private CustomerFileService customerFileService;

    // ── REGISTER ───────────────────────────────────────────────────────

    // Shows the registration page
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("customer", new User());
        return "register";
    }

    // Handles registration form submission
    @PostMapping("/register")
    public String handleRegister(@ModelAttribute User customer, Model model) {
        boolean success = customerFileService.registerCustomer(customer);
        if (success) {
            model.addAttribute("message", "Registration successful! Please login.");
            return "login";
        } else {
            model.addAttribute("error", "Username already exists. Please choose another.");
            return "register";
        }
    }

    // ── GOOGLE AUTH ────────────────────────────────────────────────────
    //
    // HOW THIS WORKS:
    // The "Continue with Google" button on the login page links to GET /auth/google
    // which shows a simulation page (google-auth.html).
    //
    // In a real production project you would add this to pom.xml:
    //   <dependency>
    //     <groupId>org.springframework.boot</groupId>
    //     <artifactId>spring-boot-starter-oauth2-client</artifactId>
    //   </dependency>
    // And add these lines to application.properties:
    //   spring.security.oauth2.client.registration.google.client-id=YOUR_ID
    //   spring.security.oauth2.client.registration.google.client-secret=YOUR_SECRET
    //
    // Spring Security would then handle the Google redirect automatically.
    // The callback below demonstrates exactly what that flow does under the hood.

    // Step 1 — User clicks "Continue with Google"
    // Shows a page that simulates Google sending back the user's email and name
    @GetMapping("/auth/google")
    public String initiateGoogleAuth() {
        return "google-auth"; // loads google-auth.html
    }

    // Step 2 — Receives the Google user's email and name after authentication
    // Logs them in if account exists, or creates one automatically if not
    @GetMapping("/auth/google/callback")
    public String handleGoogleCallback(@RequestParam String email,
                                       @RequestParam String name,
                                       Model model) {
        // Check if this Google email already has an account
        User existingCustomer = customerFileService.findCustomerByEmail(email);

        if (existingCustomer != null) {
            // Account already exists — log them straight in
            model.addAttribute("customer", existingCustomer);
            model.addAttribute("message", "Welcome back! Signed in with Google.");
            return "profile";
        } else {
            // First time — create an account automatically using their Google email
            boolean created = customerFileService.registerGoogleCustomer(email, name);
            if (created) {
                User newCustomer = customerFileService.findCustomerByEmail(email);
                model.addAttribute("customer", newCustomer);
                model.addAttribute("message",
                        "Account created with Google. Please complete your profile.");
                return "profile";
            } else {
                model.addAttribute("error", "Could not create account. Please try again.");
                return "login";
            }
        }
    }

    // ── LOGIN ──────────────────────────────────────────────────────────

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
            User customer = customerFileService.findCustomer(username);
            model.addAttribute("customer", customer);
            return "profile";
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }

    // ── PROFILE ────────────────────────────────────────────────────────

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

    // Handles profile update — email and phone only (no address)
    @PostMapping("/profile/update")
    public String handleUpdate(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String phone,
                               Model model) {
        boolean updated = customerFileService.updateCustomer(username, email, phone);
        if (updated) {
            User customer = customerFileService.findCustomer(username);
            model.addAttribute("customer", customer);
            model.addAttribute("message", "Profile updated successfully.");
        } else {
            model.addAttribute("error", "Update failed. Customer not found.");
        }
        return "profile";
    }
}