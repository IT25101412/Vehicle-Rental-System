package com.example.vehicalrentalserviceplatform.service;

import com.example.vehicalrentalserviceplatform.model.*;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {
    private final String FILE_PATH = "vehicles.txt";
    private List<Vehicle> vehicles = new ArrayList<>();

    public VehicleService() {
        loadVehicles();
    }

    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }

    private void loadVehicles() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];

                // Logic to reconstruct the correct object type
                if (type.equals("CAR")) {
                    vehicles.add(new Car(parts[1], parts[2], parts[3], parts[4], parts[5],
                            Double.parseDouble(parts[6]), parts[7], Double.parseDouble(parts[8]),
                            Boolean.parseBoolean(parts[9]), Integer.parseInt(parts[10]), parts[11]));
                }
                // Add else-if blocks for MOTORCYCLE, SUV, VAN...
            }
        } catch (IOException e) {
            System.out.println("No existing inventory found. Starting fresh.");
        }
    }
}
