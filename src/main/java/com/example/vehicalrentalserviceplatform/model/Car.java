package com.example.vehicalrentalserviceplatform.model;

public class Car extends Vehicle{
    private int numberOfSeats;
    private String carType; //whether a hatchback or a sedan

    //Constructor for file loading

    public Car(String vehicleId, String year, String make, String model, String fuelType, double mileage, String vehicleImageFileName, double rentalRate, boolean isAvailable, int numberOfSeats, String carType) {
        super(vehicleId, year, make, model, fuelType, mileage, vehicleImageFileName, rentalRate, isAvailable);
        this.numberOfSeats=numberOfSeats;
        setCarType(carType);
    }

    //Constructor for new car

    public Car(String year, String make, String model, String fuelType, double mileage, String vehicleImageFileName, double rentalRate, int numberOfSeats, String carType) {
        super(year, make, model, fuelType, mileage, vehicleImageFileName, rentalRate);
        this.numberOfSeats=numberOfSeats;
        setCarType(carType);
    }
    //Getters

    public String getCarType() {
        return carType;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    //Setters

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setCarType(String carType) {
        if (carType.equalsIgnoreCase("Hatchback")) {
            this.carType = "Hatchback";
        } else {
            this.carType = "Sedan"; // Defaults to Sedan for any case combination of Standard or any invalid input to prevent errors
        }
    }
    @Override
    public String toString() {
        return "CAR," + super.toString() + "," + numberOfSeats + "," + carType;
    }
}
