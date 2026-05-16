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
            grid.innerHTML = '<p class="text-muted" style="padding: 20px;">No vehicles available in this category.</p>';
            return;
        }

        filtered.forEach(vehicle => {
            const card = document.createElement('div');
            card.className = 'card';

            // THE FIX: These constraints prevent the massive images from breaking the grid
            card.style.display = 'flex';
            card.style.flexDirection = 'column';
            card.style.minWidth = '0';        // Stops flexbox from expanding infinitely
            card.style.overflow = 'hidden';   // Hides anything that bleeds over the edge

            // 2. Build the internal HTML
            card.innerHTML = `
                <img src="/images/${vehicle.vehicleImageFileName}" alt="${vehicle.make}" 
                     onerror="this.src='https://via.placeholder.com/250'" 
                     style="width: 100%; max-width: 100%; height: 200px; object-fit: cover; border-radius: 12px; margin-bottom: 16px; display: block;">
                
                <h3 style="margin-bottom: 8px; font-size: 1.25rem; color: #111827; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                    ${vehicle.year} ${vehicle.make} ${vehicle.model}
                </h3>
                
                <div style="margin-bottom: 20px;">
                    <p class="text-muted" style="margin-bottom: 4px; font-size: 0.95rem;"><strong>Fuel:</strong> ${vehicle.fuelType}</p>
                    <p class="text-muted" style="font-size: 0.95rem;"><strong>Cost:</strong> Rs. ${vehicle.rentalRate} / day</p>
                </div>
                
                <div style="margin-top: auto;">
                    <button class="button" style="width: 100%;" onclick="goToBooking('${vehicle.vehicleId}')">Book Now</button>
                </div>
            `;
            grid.appendChild(card);
        });

    } catch (error) {
        console.error("Error:", error);
    }
}

// 3. Keep your redirect function, but ensure it has the leading slash
function goToBooking(id) {
    window.location.href = `/bookVehicle?id=${id}`;
}