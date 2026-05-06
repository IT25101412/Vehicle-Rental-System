// Load inventory as soon as the page opens
window.onload = fetchInventory;

async function fetchInventory() {
    try {
        const response = await fetch('/api/vehicles'); // Calls your @GetMapping
        const vehicles = await response.json();

        const tableBody = document.getElementById('inventoryBody');
        tableBody.innerHTML = ''; // Clear existing rows

        vehicles.forEach(vehicle => {
            const row = document.createElement('tr');

            // Extract the first 8 characters of the UUID for a cleaner table look
            const shortId = vehicle.vehicleId.substring(0, 8);

            row.innerHTML = `
                <td><img src="/images/${vehicle.vehicleImageFileName}" width="80" alt="vehicle"></td>
                <!-- Show short ID, but put full ID in hover title -->
                <td title="${vehicle.vehicleId}">${shortId}...</td>
                <td>${vehicle.make} ${vehicle.model} (${vehicle.year})</td>
                <td>${vehicle.type || 'Vehicle'}</td> 
                <td>${vehicle.rentalRate}</td>
                <td>${vehicle.isAvailable ? 'Available' : 'Rented'}</td>
                <td>
                    <!-- IMPORTANT: Keep the FULL vehicle.vehicleId for the buttons -->
                    <button onclick="editVehicle('${vehicle.vehicleId}')">Update</button>
                    <button onclick="deleteVehicle('${vehicle.vehicleId}')" style="color:red;">Delete</button>
                </td>
            `;
            tableBody.appendChild(row);
        });
    } catch (error) {
        console.error("Error loading inventory:", error);
    }
}

// Redirects to the form with the full ID in the URL for "Update Mode"
function editVehicle(id) {
    window.location.href = `vehicleForm.html?id=${id}`;
}

// Calls your @DeleteMapping in the Controller with the full ID
async function deleteVehicle(id) {
    if (confirm("Are you sure you want to delete this vehicle?")) {
        const response = await fetch(`/api/vehicles/delete/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert("Vehicle removed successfully!");
            fetchInventory(); // Refresh the table instantly
        } else {
            alert("Failed to delete vehicle.");
        }
    }
}