package com.example.vehicalrentalserviceplatform.controller;

import com.example.vehicalrentalserviceplatform.service.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
public class DeleteBookingController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String targetID = request.getParameter("transactionId");
        BookingService service = new BookingService();
        service.deleteBooking(targetID);
        response.sendRedirect("reservationHistory.jsp");
    }
}
