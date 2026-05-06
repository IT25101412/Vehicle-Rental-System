package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.model.*;
import com.example.vehicalrentalserviceplatform.service.VehicleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleApiController {

    private final VehicleService vehicleService;
    // Define where images will be stored physically on your PC
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
            @RequestParam("vehicleId") String vehicleId,
            @RequestParam("make") String make,
            @RequestParam("model") String model,
            @RequestParam("year") String year,
            @RequestParam("regNo") String regNo,
            @RequestParam("fuel") String fuel,
            @RequestParam("rate") double rate,
            @RequestParam("mileage") double mileage,
            @RequestParam(value = "seats", required = false) Integer seats,
            @RequestParam(value = "driveTrain", required = false) String driveTrain,
            @RequestParam(value = "motoType", required = false) String motoType,
            @RequestParam("image") MultipartFile imageFile) throws IOException {

        // 1. Handle the Image Upload
        String fileName = vehicleId + "_" + imageFile.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, imageFile.getBytes());

        // 2. Reconstruct the correct Subclass based on type
        Vehicle v;
        if (type.equals("CAR")) {
            v = new Car(vehicleId, make, model, year, regNo, rate, fuel, mileage, true, seats, fileName);
        } else if (type.equals("MOTORCYCLE")) {
            v = new Motorcycle(vehicleId, make, model, year, regNo, rate, fuel, mileage, true, motoType);
        } else if (type.equals("SUV")) {
            v = new Suv(vehicleId, make, model, year, regNo, rate, fuel, mileage, true, seats, driveTrain);
        } else {
            v = new Van(vehicleId, make, model, year, regNo, rate, fuel, mileage, true, seats, driveTrain);
        }

        vehicleService.addVehicle(v);
        return "Vehicle and image uploaded successfully!";
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        vehicleService.deleteVehicle(id);
    }
}