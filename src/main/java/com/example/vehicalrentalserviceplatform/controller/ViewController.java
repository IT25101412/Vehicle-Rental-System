package com.example.vehicalrentalserviceplatform.controller; // Update to your package

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Notice this is @Controller, NOT @RestController
public class ViewController {

    @GetMapping("/inventory")
    public String showInventory() {
        return "inventory"; // This tells Spring to look for /templates/inventory.html
    }

    @GetMapping("/catalog")
    public String showCatalog() {
        return "catalog"; // Looks for /templates/catalog.html
    }

    @GetMapping("/vehicleForm")
    public String showVehicleForm() {
        return "vehicleForm"; // Looks for /templates/vehicleForm.html
    }

    @GetMapping("/bookVehicle")
    public String showBookVehicle() {
        return "bookVehicle"; // Looks for /templates/bookVehicle.html
    }
}