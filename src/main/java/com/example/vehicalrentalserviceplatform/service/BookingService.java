package com.example.vehicalrentalserviceplatform.service;

import com.example.vehicalrentalserviceplatform.model.Booking;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private static final String FILE_NAME = "booking.txt";

    public boolean isVehicleAvailable(String vehicleId, String StartDate){

        List<Booking> activeBookings = getAllBookings();

        for(Booking x : activeBookings){

            if (x.getVehicleId().equals(vehicleId) && x.getBookingStatus().equals("Active")){
                return false;
            }
        }
        return true;
    }

    public  void  createBooking(Booking newBooking){

        if(!isVehicleAvailable(newBooking.getVehicleId(),newBooking.getStartDate())){
            System.out.println("Error: Vehicle is already booked");
            return;
        }

        try(FileWriter fileWriter = new FileWriter(FILE_NAME, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter out = new PrintWriter(bufferedWriter)){

            out.println(newBooking.toString());
            System.out.println("Booking Successful");

        }catch(IOException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public List<Booking> getAllBookings(){
        List<Booking> bookingList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            while ((line = bufferedReader.readLine()) != null){
                String[] data = line.split(",");

                if(data.length == 6){
                    Booking booking = new Booking(data[0],data[1],data[2],data[3],data[4],data[5]);
                    bookingList.add(booking);
                }
            }
        }catch (IOException e){
            System.out.println("No existing bookings found or error reading file.");
        }
        return bookingList;

    }
}
