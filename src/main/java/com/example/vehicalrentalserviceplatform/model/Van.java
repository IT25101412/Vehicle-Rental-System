package com.example.vehicalrentalserviceplatform.model;

public class Van extends Vehicle{
    private int numberOfSeats;
    private String driveTrain; //whether van is awd 2wd or 4wd

    //Constructor for file loading

    public Van(String vehicleId, String year, String make, String model, String fuelType, double mileage, String vehicleImageFileName, double rentalRate, boolean isAvailable, int numberOfSeats, String driveTrain) {
        super(vehicleId, year, make, model, fuelType, mileage, vehicleImageFileName, rentalRate, isAvailable);
        this.numberOfSeats=numberOfSeats;
        setDriveTrain(driveTrain);
    }

    //Constructor for new Van

    public Van(String year, String make, String model, String fuelType, double mileage, String vehicleImageFileName, double rentalRate, int numberOfSeats, String driveTrain) {
        super(year, make, model, fuelType, mileage, vehicleImageFileName, rentalRate);
        this.numberOfSeats=numberOfSeats;
        setDriveTrain(driveTrain);
    }
    //Getters

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public String getDriveTrain() {
        return driveTrain;
    }
    //Setters

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setDriveTrain(String driveTrain) {
        if (driveTrain.equalsIgnoreCase("4WD")) {
            this.driveTrain = "4WD";
        } else if (driveTrain.equalsIgnoreCase("AWD")) {
            this.driveTrain = "AWD";
        } else {
            this.driveTrain = "2WD"; // Defaults to 2WD for any case combination of Standard or any invalid input to prevent errors
        }
    }
    @Override
    public String toString() {
        return "VAN," + super.toString() + "," + numberOfSeats + "," + driveTrain;
    }
}
