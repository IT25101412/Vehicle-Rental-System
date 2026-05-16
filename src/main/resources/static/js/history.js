document.addEventListener("DOMContentLoaded", function() {
    loadBookings();
});

function loadBookings() {
    const currentUser = document.getElementById("loggedInUsername").value;

    if (!currentUser) {
            console.error("No user logged in!");
            document.getElementById("bookingTableBody").innerHTML = "<tr><td colspan='6'>Please log in to view history.</td></tr>";
            return;
    }

    fetch(`/api/bookings?customer=${currentUser}`)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("bookingTableBody");
            tableBody.innerHTML = "";

            data.forEach(booking => {

                // --- THE SMART BUTTON LOGIC ---
                let actionHtml = '';

                if (booking.bookingStatus === 'Pending') {
                    // Show a waiting message, plus let them Edit or Delete while they wait
                    actionHtml = `
                        <span style="color: #ffc107; font-weight: bold; display: block; margin-bottom: 5px;">⏳ Awaiting Approval</span>
                        <div class="action-buttons">
                            <a href="/editBooking?id=${booking.transactionId}" class="button" style="padding: 4px 8px; font-size: 0.85em;">Edit</a>
                            <button onclick="submitDelete('${booking.transactionId}')" class="button secondary" style="padding: 4px 8px; font-size: 0.85em; background-color: #dc3545; border-color: #dc3545;">Delete</button>
                        </div>
                    `;
                } else if (booking.bookingStatus === 'Approved') {
                    // The Admin approved it! Give them the Pay Now button
                    actionHtml = `
                        <a href="/checkout?transactionId=${booking.transactionId}" class="button" style="background-color: #28a745; border-color: #28a745; font-weight: bold;">💳 Pay Now</a>
                    `;
                } else if (booking.bookingStatus === 'Paid') {
                    // They paid successfully
                    actionHtml = `
                        <span style="color: #28a745; font-weight: bold;">✅ Payment Complete</span>
                    `;
                } else if (booking.bookingStatus === 'Rejected') {
                    // The Admin rejected the booking
                    actionHtml = `
                        <span style="color: #dc3545; font-weight: bold;">❌ Booking Declined</span>
                    `;
                } else {
                    // Fallback for any other status (like 'Cancelled')
                    actionHtml = `<strong>${booking.bookingStatus}</strong>`;
                }

                // --- BUILD THE ROW ---
                const row = `
                    <tr>
                        <td>${booking.customerName}</td>
                        <td>${booking.vehicleId}</td>
                        <td>${booking.startDate}</td>
                        <td>${booking.returnDate}</td>
                        <td><strong>${booking.bookingStatus}</strong></td>
                        <td>
                            ${actionHtml}
                        </td>
                    </tr>
                `;

                tableBody.innerHTML += row;
            });

        })
        .catch(error => {
            console.error("Error fetching the bookings:", error);
            document.getElementById("bookingTableBody").innerHTML = "<tr><td colspan='6'>Error loading bookings. Please try again later.</td></tr>";
        });
}

// This creates an invisible form, submits it to the Java Controller, and deletes
function submitDelete(transactionId) {
    if (confirm("Are you sure you want to cancel this booking?")) {

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