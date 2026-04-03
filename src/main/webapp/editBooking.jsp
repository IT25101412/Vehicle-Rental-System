<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
    <h2>Update Your Reservation</h2>
    <form action="updateBooking" method="POST" >
        <input type="hidden" name="transactionId" value="<%= request.getParameter("transactionId") %>">
        <input type="hidden" name="bookingStatus" value="<%= request.getParameter("bookingStatus") %>">
        <p>
            <label>Vehicle ID:</label><br>
            <input type="text" name="vehicleId" value="<%= request.getParameter("vehicleId") %>" required>
        </p>
        <p>
            <label>Start Date:</label><br>
            <input type="date" name="startDate" value="<%= request.getParameter("startDate") %>" required>
        </p>

        <p>
            <label>Return Date:</label><br>
            <input type="date" name="returnDate" value="<%= request.getParameter("returnDate") %>" required>
        </p>

        <button type="submit" style="color: green;">Save Changes</button>
        <a href="reservationHistory.jsp">Cancel</a>
    </form>>
</body>>
</html>