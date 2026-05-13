package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.model.Admin;
import com.example.vehicalrentalserviceplatform.service.AdminStaffService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminLoginController {

    private final AdminStaffService adminStaffService = new AdminStaffService();

    // 1. Shows the login page
    @GetMapping("/admin-login")
    public String showAdminLoginPage(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        return "admin-login";
    }

    // 2. Handles the login form submission
    @PostMapping("/admin-login")
    public String handleAdminLogin(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpSession session) {

        Admin admin = adminStaffService.authenticate(username, password);

        if (admin != null) {
            // Save admin details in the session
            session.setAttribute("loggedInAdmin", admin.getUsername());
            session.setAttribute("role", admin.getRole());

            return "redirect:/admin/dashboard"; // Success! Go to dashboard
        } else {
            return "redirect:/admin-login?error=true"; // Failed! Reload with error
        }
    }

    // 3. Handles logging out
    @GetMapping("/admin-logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destroys the session
        return "redirect:/admin-login";
    }
}