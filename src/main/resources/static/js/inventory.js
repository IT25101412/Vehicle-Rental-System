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
            tableBody.innerHTML = `
                <tr>
                    <td colspan="7" class="p-8 text-center text-on-primary-container">
                        <div class="flex flex-col items-center gap-3">
                            <span class="material-symbols-outlined text-4xl opacity-50">directions_car</span>
                            <p>No vehicles currently in inventory.</p>
                        </div>
                    </td>
                </tr>`;
            return;
        }

        vehicles.forEach(vehicle => {
            const row = document.createElement('tr');
            // Add Tailwind classes to the row
            row.className = "border-b border-on-surface-variant/10 hover:bg-[#2A2A2C] transition-colors group";

            // Check availability to determine badge color (Tailwind)
            const isAvailable = String(vehicle.available).toLowerCase() === 'true';
            const badgeClass = isAvailable ? 'bg-green-500/20 text-green-500' : 'bg-red-500/20 text-red-500';
            const badgeText = isAvailable ? 'AVAILABLE' : 'RENTED';

            row.innerHTML = `
                <td class="p-4">
                    <div class="w-20 h-14 rounded-lg overflow-hidden bg-[#0A0A0A] border border-on-surface-variant/20 shadow-sm group-hover:border-on-surface-variant/40 transition-colors">
                        <img src="/images/${vehicle.vehicleImageFileName}"
                             onerror="this.src='https://via.placeholder.com/80'"
                             class="w-full h-full object-cover">
                    </div>
                </td>

                <td class="p-4 font-mono text-sm text-on-surface-variant whitespace-nowrap">
                    ${vehicle.vehicleId}
                </td>

                <td class="p-4">
                    <div class="font-label-md text-label-md text-on-primary">${vehicle.make} ${vehicle.model}</div>
                    <div class="font-body-sm text-sm text-on-primary-container opacity-70">${vehicle.year}</div>
                </td>

                <td class="p-4 font-body-md text-body-md text-on-primary-container">
                    ${vehicle.type || 'Vehicle'}
                </td>

                <td class="p-4 font-body-md text-body-md text-on-primary-container font-semibold">
                    Rs. ${vehicle.rentalRate}
                </td>

                <td class="p-4">
                    <span class="px-2 py-1 rounded text-xs font-bold tracking-wide ${badgeClass}">
                        ${badgeText}
                    </span>
                </td>

                <td class="p-4 text-right">
                    <div class="flex justify-end gap-2 opacity-80 group-hover:opacity-100 transition-opacity">
                        <button class="bg-yellow-600/10 hover:bg-yellow-600/30 text-yellow-500 border border-yellow-600/30 px-3 py-1.5 rounded text-sm transition-colors flex items-center gap-1" onclick="editVehicle('${vehicle.vehicleId}')">
                            <span class="material-symbols-outlined text-[16px]">edit</span> Edit
                        </button>
                        <button class="bg-red-600 hover:bg-red-500 text-white px-3 py-1.5 rounded text-sm transition-colors flex items-center gap-1" onclick="deleteVehicle('${vehicle.vehicleId}')">
                            <span class="material-symbols-outlined text-[16px]">delete</span> Delete
                        </button>
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