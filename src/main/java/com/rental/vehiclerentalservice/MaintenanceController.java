package com.rental.vehiclerentalservice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    // SHOW maintenance log entry form + history
    @GetMapping
    public String maintenancePage(Model model) {
        List<MaintenanceRecord> records = MaintenanceFileHandler.loadAllRecords();
        model.addAttribute("records", records);
        return "maintenance";
    }

    // CREATE — Submit new maintenance record
    @PostMapping("/add")
    public String addRecord(@RequestParam String vehicleId,
                            @RequestParam String serviceType,
                            @RequestParam String serviceDate,
                            @RequestParam String notes) {
        String recordId = "REC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        MaintenanceRecord record = new MaintenanceRecord(
                recordId, vehicleId, serviceType, serviceDate, "Pending", notes
        );
        MaintenanceFileHandler.addRecord(record);
        return "redirect:/maintenance";
    }

    // UPDATE — Change status of a record
    @PostMapping("/update")
    public String updateStatus(@RequestParam String recordId,
                               @RequestParam String status) {
        MaintenanceFileHandler.updateStatus(recordId, status);
        return "redirect:/maintenance";
    }

    // DELETE — Remove a record
    @GetMapping("/delete/{recordId}")
    public String deleteRecord(@PathVariable String recordId) {
        MaintenanceFileHandler.deleteRecord(recordId);
        return "redirect:/maintenance";
    }

    // SHOW alert for a specific record (Polymorphism demo)
    @GetMapping("/alert/{recordId}")
    @ResponseBody
    public String getAlert(@PathVariable String recordId) {
        List<MaintenanceRecord> records = MaintenanceFileHandler.loadAllRecords();
        for (MaintenanceRecord r : records) {
            if (r.getRecordId().equals(recordId)) {
                return r.getMaintenanceAlert();
            }
        }
        return "Record not found.";
    }
}