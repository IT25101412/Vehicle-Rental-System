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

            row.innerHTML = `
                <td><img src="/images/${vehicle.vehicleImageFileName}" width="80"></td>
                <td>${vehicle.vehicleId}</td>
                <td>${vehicle.make} ${vehicle.model} (${vehicle.year})</td>
                <td>${vehicle.type}</td>
                <td>${vehicle.rentalRate}</td>
                <td>${vehicle.isAvailable ? 'Available' : 'Rented'}</td>
                <td>
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

// Redirects to the form with the ID in the URL for "Update Mode"
function editVehicle(id) {
    window.location.href = `vehicleForm.html?id=${id}`;
}

// Calls your @DeleteMapping in the Controller
async function deleteVehicle(id) {
    if (confirm("Are you sure you want to delete this vehicle?")) {
        const response = await fetch(`/api/vehicles/delete/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert("Vehicle removed from vehicles.txt!");
            fetchInventory(); // Refresh the table instantly
        } else {
            alert("Failed to delete vehicle.");
        }
    }
}