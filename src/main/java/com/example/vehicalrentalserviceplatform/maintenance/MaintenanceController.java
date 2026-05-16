package com.example.vehicalrentalserviceplatform.maintenance;

import com.example.vehicalrentalserviceplatform.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    private VehicleService vehicleService;

    // READ - Show maintenance log entry form and service history
    @GetMapping
    public String maintenancePage(HttpSession session, Model model) {

        String role = (String) session.getAttribute("role");
        if (role == null || (!role.equals("ADMIN") && !role.equals("STAFF"))) {
            return "redirect:/admin-login"; // Kick them out if not authorized
        }

        List<MaintenanceRecord> records = MaintenanceFileHandler.loadAllRecords();
        model.addAttribute("records", records);

        model.addAttribute("vehicles",vehicleService.getAllVehicles());

        return "maintenance";

    }

    // CREATE - Add new maintenance record
    @PostMapping("/add")
    public String addRecord(@RequestParam String vehicleId,
                            @RequestParam String vehicleType,
                            @RequestParam String serviceType,
                            @RequestParam String serviceDate,
                            @RequestParam String notes) {

        String recordId = "REC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        MaintenanceRecord record = new MaintenanceRecord(
                recordId,
                vehicleId,
                vehicleType,
                serviceType,
                serviceDate,
                "Pending",
                notes
        );

        MaintenanceFileHandler.addRecord(record);

        return "redirect:/maintenance";
    }

    // UPDATE - Change status of a maintenance record
    @PostMapping("/update")
    public String updateStatus(@RequestParam String recordId,
                               @RequestParam String status) {

        MaintenanceFileHandler.updateStatus(recordId, status);

        return "redirect:/maintenance";
    }

    // DELETE - Remove a maintenance record
    @GetMapping("/delete/{recordId}")
    public String deleteRecord(@PathVariable String recordId) {

        MaintenanceFileHandler.deleteRecord(recordId);

        return "redirect:/maintenance";
    }

    // Polymorphism demonstration - Show vehicle type based maintenance alert
    @GetMapping("/alert/{recordId}")
    @ResponseBody
    public String getAlert(@PathVariable String recordId) {

        MaintenanceRecord record = MaintenanceFileHandler.findRecordById(recordId);

        if (record != null) {
            return record.getMaintenanceAlert();
        }

        return "Record not found.";
    }
}