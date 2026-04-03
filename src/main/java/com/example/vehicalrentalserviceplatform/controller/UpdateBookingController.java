package com.example.vehicalrentalserviceplatform.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;

public class UpdateBookingController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        String transactionId = request.getParameter("transactionId");
        String newVehicleId = request.getParameter("vehicleId");
        String newStartDate = request.getParameter("startDate");
        String newReturnDate = request.getParameter("returnDate");
        String status = request.getParameter("bookingStatus");

        File originalFile = new File("booking.txt");
        File tempFile = new File("temp_booking.txt");

        try(BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.trim().isEmpty()) continue;

                String[] data = currentLine.split(",");

                if(data[0].equals(transactionId)){
                    String originalCustomerName = data[1];
                    String updatedLine = transactionId + "," + originalCustomerName + "," + newVehicleId + "," + newStartDate + "," + newReturnDate + "," + status;
                    writer.write(updatedLine);
                }
                else {
                    writer.write(currentLine);
                }
                writer.newLine();
            }
        }

        if(originalFile.delete()){
            tempFile.renameTo(originalFile);
        }

        response.sendRedirect("reservationHistory.jsp");
    }
}
