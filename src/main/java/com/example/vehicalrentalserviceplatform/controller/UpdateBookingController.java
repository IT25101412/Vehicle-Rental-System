package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.service.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;

public class UpdateBookingController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String transactionId = request.getParameter("transactionId");
        String newVehicleId = request.getParameter("vehicleId");
        String newStartDate = request.getParameter("startDate");
        String newReturnDate = request.getParameter("returnDate");
        String status = request.getParameter("bookingStatus");

        BookingService service = new BookingService();
        service.updateBooking(transactionId,newVehicleId,newStartDate, newReturnDate, status);

        response.sendRedirect("reservationHistory.html");
    }
}
