package com.example.vehicalrentalserviceplatform.model;

public class Motorcycle extends Vehicle{
    private String motorcycleType; // whether a standard or scooter

    //Constructor for file loading

    public Motorcycle(String vehicleId, String year, String make, String model, String fuelType, double mileage, String vehicleImageFileName, double rentalRate, boolean isAvailable, String motorcycleType) {
        super(vehicleId, year, make, model, fuelType, mileage, vehicleImageFileName, rentalRate, isAvailable);
        setMotorcycleType(motorcycleType);
    }

    //Constructor for new Motorcycle

    public Motorcycle(String year, String make, String model, String fuelType, double mileage, String vehicleImageFileName, double rentalRate, String motorcycleType) {
        super(year, make, model, fuelType, mileage, vehicleImageFileName, rentalRate);
            setMotorcycleType(motorcycleType);
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
