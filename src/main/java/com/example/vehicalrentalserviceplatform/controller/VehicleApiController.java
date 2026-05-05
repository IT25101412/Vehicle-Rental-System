package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.model.Vehicle;
import com.example.vehicalrentalserviceplatform.service.VehicleService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles") // Base URL for JS fetch calls
public class VehicleApiController {

    private final VehicleService vehicleService;

    // Spring Boot automatically injects the singleton VehicleService here
    public VehicleApiController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // READ: Returns JSON list of vehicles for the JS catalogue
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    // CREATE: Adds a new vehicle from a JS JSON object
    @PostMapping("/add")
    public String addVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.addVehicle(vehicle);
        return "Vehicle added successfully!";
    }

    // DELETE: Removes a vehicle using its ID from the URL
    @DeleteMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable String id) {
        boolean success = vehicleService.deleteVehicle(id);
        return success ? "Vehicle deleted!" : "Vehicle not found.";
    }

    // UPDATE: Updates an existing vehicle
    @PutMapping("/update/{id}")
    public String updateVehicle(@PathVariable String id, @RequestBody Vehicle vehicle) {
        vehicleService.updateVehicle(id, vehicle);
        return "Vehicle updated!";
    }
}