document.addEventListener("DOMContentLoaded", function() {
    loadBookings();
});

function loadBookings() {
    const currentUser = "John Doe"; // Replace with actual logged-in user data

    fetch(`/api/bookings?customer=${currentUser}`)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("bookingTableBody");
            tableBody.innerHTML = "";

            data.forEach(booking => {

                const row = `
                    <tr>
                        <td>${booking.transactionId}</td>
                        <td>${booking.customerName}</td>
                        <td>${booking.vehicleId}</td>
                        <td>${booking.startDate}</td>
                        <td>${booking.returnDate}</td>
                        <td>${booking.bookingStatus}</td>
                        <td>
                            <div class="action-buttons">
                                <a href="/editBooking.html?id=${booking.transactionId}" class="button">Edit</a>

                                <button onclick="submitDelete('${booking.transactionId}')" class="button secondary">Delete</button>
                            </div>
                        </td>
                    </tr>
                `;

                tableBody.innerHTML += row;
            });

        })
        .catch(error => {
            console.error("Error fetching the bookings:", error);
        });
}

// This creates an invisible form submits it to Java Controller and deletes
function submitDelete(transactionId) {
    if (confirm("Are you sure you want to delete this booking?")) {

        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '/deleteBooking';

        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'transactionId';
        input.value = transactionId;

        form.appendChild(input);
        document.body.appendChild(form);

        form.submit();
    }
}