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

    // CREATE: Add vehicle and save to file[cite: 2]
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        saveVehiclesToFile();
    }

    // DELETE: Remove vehicle and update file[cite: 2]
    public boolean deleteVehicle(String vehicleId) {
        boolean removed = vehicles.removeIf(v -> v.getVehicleId().equals(vehicleId));
        if (removed) {
            saveVehiclesToFile();
        }
        return removed;
    }

    // UPDATE: Find and replace vehicle data[cite: 2]
    public void updateVehicle(String id, Vehicle updatedVehicle) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getVehicleId().equals(id)) {
                vehicles.set(i, updatedVehicle);
                saveVehiclesToFile();
                break;
            }
        }
    }

    // --- Persistence Logic ---

    private void saveVehiclesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Vehicle v : vehicles) {
                writer.println(v.toString()); // Uses your CSV-style toString[cite: 2]
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadVehicles() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 2) continue;

                String type = parts[0];
                // Reconstruction logic[cite: 2]
                if (type.equals("CAR")) {
                    vehicles.add(new Car(parts[1], parts[2], parts[3], parts[4], parts[5],
                            Double.parseDouble(parts[6]), parts[7], Double.parseDouble(parts[8]),
                            Boolean.parseBoolean(parts[9]), Integer.parseInt(parts[10]), parts[11]));
                } else if (type.equals("MOTORCYCLE")) {
                    vehicles.add(new Motorcycle(parts[1], parts[2], parts[3], parts[4], parts[5],
                            Double.parseDouble(parts[6]), parts[7], Double.parseDouble(parts[8]),
                            Boolean.parseBoolean(parts[9]), parts[10]));
                } else if (type.equals("SUV")) {
                    vehicles.add(new Suv(parts[1], parts[2], parts[3], parts[4], parts[5],
                            Double.parseDouble(parts[6]), parts[7], Double.parseDouble(parts[8]),
                            Boolean.parseBoolean(parts[9]), Integer.parseInt(parts[10]), parts[11]));
                } else if (type.equals("VAN")) {
                    vehicles.add(new Van(parts[1], parts[2], parts[3], parts[4], parts[5],
                            Double.parseDouble(parts[6]), parts[7], Double.parseDouble(parts[8]),
                            Boolean.parseBoolean(parts[9]), Integer.parseInt(parts[10]), parts[11]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }
}