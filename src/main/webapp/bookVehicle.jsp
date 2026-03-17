<!DOCTYPE html>
<html>
<head>
    <title>Book a Vehicle</title>
</head>
<body>

<h2>Reserve Your Vehicle</h2>

<form action="createBooking" method="POST">

    <label for="user">Username:</label><br>
    <input type="text" id="user" name="customerUsername" required><br><br>

    <label for="vehicle">Vehicle ID:</label><br>
    <input type="text" id="vehicle" name="vehicleId" required><br><br>

    <label for="start">Start Date (YYYY-MM-DD):</label><br>
    <input type="date" id="start" name="startDate" required><br><br>

    <label for="return">Return Date (YYYY-MM-DD):</label><br>
    <input type="date" id="return" name="returnDate" required><br><br>

    <input type="submit" value="Confirm Booking">

</form>

</body>
</html>