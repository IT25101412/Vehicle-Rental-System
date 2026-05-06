package com.example.vehicalrentalserviceplatform.model;

public class Motorcycle extends Vehicle{
    private String motorcycleType; // whether a standard or scooter

    // Updated for Controller Compatibility - No Registration Number, ID at end
    public Motorcycle(String make, String model, String year, double rentalRate,
                      String fuelType, double mileage, boolean isAvailable,
                      String motorcycleType, String vehicleImageFileName, String vehicleId) {
        super(make, model, year, rentalRate, fuelType, mileage, isAvailable, vehicleImageFileName, vehicleId);
        this.motorcycleType = motorcycleType;
    }

    //Getter

    public String getMotorcycleType() {
        return motorcycleType;
    }
    //Setter

    public void setMotorcycleType(String motorcycleType) {
        if (motorcycleType.equalsIgnoreCase("Scooter")) {
            this.motorcycleType = "Scooter";
        } else {
            this.motorcycleType = "Standard"; // Defaults to Standard for any case combination of Standard or any invalid input to prevent errors
        }
    }
    @Override
    public String toString() {
        return "MOTORCYCLE," + super.toString() + "," + motorcycleType;
    }
}
