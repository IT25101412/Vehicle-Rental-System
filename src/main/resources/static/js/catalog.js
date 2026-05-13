window.onload = loadCatalog;

async function loadCatalog() {
    try {
        const response = await fetch('/api/vehicles');
        const vehicles = await response.json();

        const typeFilter = document.getElementById("typeFilter").value;
        const grid = document.getElementById('catalogGrid');
        grid.innerHTML = '';

        // Filter based on the 'available' key found in your console screenshot
        const filtered = vehicles.filter(v => {
            const isAvail = String(v.available).toLowerCase() === "true";
            const matchesType = (typeFilter === "ALL" || v.type === typeFilter);
            return isAvail && matchesType;
        });

        if (filtered.length === 0) {
            grid.innerHTML = '<p>No vehicles available in this category.</p>';
            return;
        }

        filtered.forEach(vehicle => {
            const card = document.createElement('div');
            card.className = 'vehicle-card';
            card.innerHTML = `
                <img src="/images/${vehicle.vehicleImageFileName}" alt="${vehicle.make}" onerror="this.src='https://via.placeholder.com/150'">
                <h3>${vehicle.make} ${vehicle.model} (${vehicle.year})</h3>
                <p><strong>Fuel:</strong> ${vehicle.fuelType}</p>
                <p><strong>Cost:</strong> Rs. ${vehicle.rentalRate} / day</p>
                <button onclick="goToBooking('${vehicle.vehicleId}')">Book Now</button>
            `;
            grid.appendChild(card);
        });

    } catch (error) {
        console.error("Error:", error);
    }
}

function goToBooking(id) {
    window.location.href = `bookVehicle?id=${id}`;
}