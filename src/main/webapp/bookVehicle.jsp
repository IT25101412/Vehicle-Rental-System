<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vehicle Rental | Book Now</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">

            <div class="card shadow-sm border-0">
                <div class="card-header bg-primary text-white text-center py-3">
                    <h4 class="mb-0">Reserve Your Vehicle</h4>
                </div>

                <div class="card-body p-4">
                    <form action="createBooking" method="POST">

                        <div class="mb-3">
                            <label class="form-label fw-bold">Username</label>
                            <input type="text" class="form-control" name="username" placeholder="e.g. Sanithu" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Vehicle ID</label>
                            <input type="text" class="form-control" name="vehicleId" placeholder="e.g. SUV-999" required>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-bold">Start Date</label>
                                <input type="date" class="form-control" name="startDate" required>
                            </div>
                            <div class="col-md-6 mb-4">
                                <label class="form-label fw-bold">Return Date</label>
                                <input type="date" class="form-control" name="returnDate" required>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary w-100 py-2 fw-bold">Confirm Booking</button>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>

</body>
</html>