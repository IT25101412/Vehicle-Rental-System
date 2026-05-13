package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.service.AdminStaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.vehicalrentalserviceplatform.service.BookingService;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final AdminStaffService adminStaffService = new AdminStaffService();
    private final BookingService bookingService = new BookingService();

    @GetMapping
    public String adminHome() {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("admins", adminStaffService.getAllAdmins());
        model.addAttribute("logs", adminStaffService.getAllActivityLogs());

        model.addAttribute("allBookings", bookingService.getAllBookings());
        return "admin-dashboard";
    }

    @GetMapping("/registration")
    public String registration(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        return "admin-registration";
    }

    @GetMapping("/activity-log")
    public String activityLog(Model model) {
        model.addAttribute("logs", adminStaffService.getAllActivityLogs());
        return "admin-activity-log";
    }

    @GetMapping("/edit")
    public String editAdmin(
            @RequestParam String userId,
            @RequestParam String fullName,
            @RequestParam String username,
            @RequestParam String role,
            @RequestParam String employeeType,
            Model model
    ) {
        model.addAttribute("userId", userId);
        model.addAttribute("fullName", fullName);
        model.addAttribute("username", username);
        model.addAttribute("role", role);
        model.addAttribute("employeeType", employeeType);
        return "edit-admin-staff";
    }

    @GetMapping("/admin-dashboard.html")
    public String dashboardHtmlFromAdminPrefix(@RequestParam(required = false) String message) {
        if (message == null || message.isBlank()) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/admin/dashboard?message=" + message;
    }

    @GetMapping("/admin-registration.html")
    public String registrationHtmlFromAdminPrefix(@RequestParam(required = false) String message) {
        if (message == null || message.isBlank()) {
            return "redirect:/admin/registration";
        }
        return "redirect:/admin/registration?message=" + message;
    }

    @GetMapping("/admin-activity-log.html")
    public String activityLogHtmlFromAdminPrefix() {
        return "redirect:/admin/activity-log";
    }

    @GetMapping("/edit-admin-staff.html")
    public String editHtmlFromAdminPrefix(
            @RequestParam String userId,
            @RequestParam String fullName,
            @RequestParam String username,
            @RequestParam String role,
            @RequestParam String employeeType
    ) {
        return "redirect:/admin/edit?userId=" + userId
                + "&fullName=" + fullName
                + "&username=" + username
                + "&role=" + role
                + "&employeeType=" + employeeType;
    }
}
