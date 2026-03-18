package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.model.Booking;
import com.example.vehicalrentalserviceplatform.service.BookingService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BookingController extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        String name = request.getParameter("customerUsername");
        String vehicleId = request.getParameter("vehicleId");
        String startDate = request.getParameter("startDate");
        String returnDate = request.getParameter("returnDate");

        Booking newBooking = new Booking(name,vehicleId,startDate,returnDate,"Pending");

        BookingService service = new BookingService();
        service.createBooking(newBooking);

        response.sendRedirect("reservationHistory.jsp");
    }
}
