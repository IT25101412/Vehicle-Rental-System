package com.example.vehicalrentalserviceplatform.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
public class DeleteBookingController extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String targetID = request.getParameter("transactionId");

        File originalFile = new File("booking.txt");
        File tempFile = new File("temp_booking.txt");

        try(BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split(",");

                if(!data[0].contains(targetID)){
                    writer.write(currentLine);
                    writer.newLine();
                }
            }

        }

        if(originalFile.delete()){
            tempFile.renameTo(originalFile);
        } else {
            System.out.println("Could not delete original file");
        }

        response.sendRedirect("reservationHistory.jsp");

    }
}
