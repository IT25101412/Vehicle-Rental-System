<%@ page import="com.example.vehicalrentalserviceplatform.model.Booking" %>
<%@ page import="com.example.vehicalrentalserviceplatform.service.BookingService" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <title>Reservation History</title>
</head>
<body>
    <h2>Active Bookings</h2>
    <table border="1">
        <tr>
            <th>Transaction ID</th>
            <th>Vehicle ID</th>
            <th>Status</th>
            <th>Start Date</th>
            <th>Return Date</th>
        </tr>

        <%
            BookingService service = new BookingService();
            List<Booking> allBookings = service.getAllBookings();

            for (Booking x: allBookings){
        %>
            <tr>
                <td><%= x.getTransactionId()%></td>
                <td><%= x.getVehicleId()%></td>
                <td><%= x.getBookingStatus()%></td>
                <td><%= x.getStartDate()%></td>
                <td><%= x.getReturnDate()%></td>
            </tr>
        <%
            }
        %>
    </table>
</body>
</html>