// Load inventory as soon as the page opens
window.onload = fetchInventory;

async function fetchInventory() {
    try {
        const response = await fetch('/api/vehicles');
        const vehicles = await response.json();

        const tableBody = document.getElementById('inventoryBody');
        tableBody.innerHTML = ''; // Clear existing rows

        // Show a nice message if the inventory is empty
        if (vehicles.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="7" class="summary-empty">No vehicles currently in inventory.</td></tr>';
            return;
        }

        vehicles.forEach(vehicle => {
            const row = document.createElement('tr');

            // Check availability to determine badge color
            const isAvailable = String(vehicle.available).toLowerCase() === 'true';

            row.innerHTML = `
                <td>
                    <img src="/images/${vehicle.vehicleImageFileName}" 
                         onerror="this.src='https://via.placeholder.com/80'" 
                         style="width: 80px; height: 56px; object-fit: cover; border-radius: 8px; display: block;">
                </td>
                
                <td style="font-family: monospace; font-weight: 600; color: #475569;">
                    ${vehicle.vehicleId}
                </td>
                
                <td>
                    <strong>${vehicle.make} ${vehicle.model}</strong> 
                    <span class="text-muted" style="margin-left: 6px;">(${vehicle.year})</span>
                </td>
                
                <td>${vehicle.type || 'Vehicle'}</td> 
                
                <td>Rs. ${vehicle.rentalRate}</td>
                
                <td>
                    <span class="status-pill ${isAvailable ? 'PAID' : 'CANCELLED'}">
                        ${isAvailable ? 'AVAILABLE' : 'RENTED'}
                    </span>
                </td>
                
                <td>
                    <div class="action-buttons" style="flex-wrap: nowrap; justify-content: flex-end;">
                        <button class="button secondary" style="min-width: 70px; padding: 6px 14px; font-size: 0.85rem;" onclick="editVehicle('${vehicle.vehicleId}')">Edit</button>
                        <button class="button" style="background-color: #dc2626; min-width: 70px; padding: 6px 14px; font-size: 0.85rem;" onclick="deleteVehicle('${vehicle.vehicleId}')">Delete</button>
                    </div>
                </td>
            `;
            tableBody.appendChild(row);
        });
    } catch (error) {
        console.error("Error loading inventory:", error);
    }
}

// Redirects to the form with the ID in the URL
function editVehicle(id) {
    window.location.href = `/vehicleForm?id=${id}`;
}

// Calls your @DeleteMapping in the Controller
async function deleteVehicle(id) {
    if (confirm("Are you sure you want to delete this vehicle? This cannot be undone.")) {
        const response = await fetch(`/api/vehicles/delete/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            fetchInventory(); // Instantly refresh the table without reloading the page!
        } else {
            alert("Failed to delete vehicle.");
        }
    }
}