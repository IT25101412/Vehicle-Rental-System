package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.model.*;
import com.example.vehicalrentalserviceplatform.service.VehicleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleApiController {

    private final VehicleService vehicleService;
    // Static path for image storage
    private final String UPLOAD_DIR = "src/main/resources/static/images/";

    public VehicleApiController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public List<Vehicle> getAll() {
        return vehicleService.getAllVehicles();
    }

    @PostMapping("/add")
    public String addVehicle(
            @RequestParam("type") String type,
            @RequestParam("make") String make,
            @RequestParam("model") String model,
            @RequestParam("year") String year,
            @RequestParam("fuel") String fuel,
            @RequestParam("rate") double rate,
            @RequestParam("mileage") double mileage,
            @RequestParam(value = "seats", required = false) Integer seats,
            @RequestParam(value = "driveTrain", required = false) String driveTrain,
            @RequestParam(value = "motoType", required = false) String motoType,
            @RequestParam("image") MultipartFile imageFile) {

        try {
            // 1. Generate unique ID automatically
            String vehicleId = UUID.randomUUID().toString();

            // 2. Handle the Image Upload using the vehicleId as a prefix
            String fileName = vehicleId + "_" + imageFile.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, imageFile.getBytes());

            // 3. Reconstruct the correct Subclass
            // ORDER: Make, Model, Year, Rate, Fuel, Mileage, Available, [Specifics], FileName, ID
            Vehicle v;
            if (type.equals("CAR")) {
                v = new Car(make, model, year, rate, fuel, mileage, true, seats, fileName, vehicleId);
            } else if (type.equals("MOTORCYCLE")) {
                v = new Motorcycle(make, model, year, rate, fuel, mileage, true, motoType, fileName, vehicleId);
            } else if (type.equals("SUV")) {
                v = new Suv(make, model, year, rate, fuel, mileage, true, seats, driveTrain, fileName, vehicleId);
            } else {
                v = new Van(make, model, year, rate, fuel, mileage, true, seats, driveTrain, fileName, vehicleId);
            }

            vehicleService.addVehicle(v);
            return "Vehicle and image uploaded successfully! ID: " + vehicleId;

        } catch (IOException e) {
            return "Error uploading image: " + e.getMessage();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        vehicleService.deleteVehicle(id);
    }
}