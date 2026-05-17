window.onload = () => {
    setupCategoryTabs();
    loadCatalog();
};

let allVehicles = [];
let activeType = "ALL";

function setupCategoryTabs() {
    const tabs = document.querySelectorAll(".category-tab");

    tabs.forEach(tab => {
        tab.addEventListener("click", () => {
            tabs.forEach(item => item.classList.remove("active"));
            tab.classList.add("active");

            activeType = tab.dataset.type;
            renderVehicles();
        });
    });
}

async function loadCatalog() {
    const grid = document.getElementById("catalogGrid");

    try {
        grid.innerHTML = `
            <div class="empty-state">
                Loading vehicles...
            </div>
        `;

        const response = await fetch("/api/vehicles");

        if (!response.ok) {
            throw new Error("Could not fetch /api/vehicles");
        }

        const data = await response.json();

        allVehicles = Array.isArray(data)
            ? data
            : data.vehicles || data.data || data.content || [];

        renderVehicles();

    } catch (error) {
        console.error(error);

        grid.innerHTML = `
            <div class="empty-state">
                <h3>Could not load vehicles</h3>
                <p>Check if your backend API <strong>/api/vehicles</strong> is running.</p>
            </div>
        `;
    }
}

function renderVehicles() {
    const grid = document.getElementById("catalogGrid");

    const filteredVehicles = allVehicles.filter(vehicle => {
        const vehicleType = String(
            vehicle.type ||
            vehicle.vehicleType ||
            vehicle.category ||
            ""
        ).toUpperCase();

        const rate = Number(vehicle.rentalRate || vehicle.rate || vehicle.price || 0);

        if (activeType === "ALL") {
            return true;
        }

        if (activeType === "PREMIUM") {
            return rate >= 25000;
        }

        return vehicleType === activeType;
    });

    grid.innerHTML = "";

    if (filteredVehicles.length === 0) {
        grid.innerHTML = `
            <div class="empty-state">
                <h3>No vehicles found</h3>
                <p>No vehicles are available in this category right now.</p>
            </div>
        `;
        return;
    }

    filteredVehicles.forEach(vehicle => {
        const card = document.createElement("article");
        card.className = "car-card";

        const vehicleId = vehicle.vehicleId || vehicle.id || "";
        const imageFile = vehicle.vehicleImageFileName || vehicle.imageFileName || vehicle.image || "";

        const imageSrc = imageFile
            ? `/images/${imageFile}`
            : "https://via.placeholder.com/900x560?text=DriveEase+Vehicle";

        const title = `${vehicle.make || ""} ${vehicle.model || ""}`.trim() || "DriveEase Vehicle";
        const type = vehicle.type || vehicle.vehicleType || "Vehicle";
        const seats = vehicle.seats || vehicle.seatCount || vehicle.capacity || getDefaultSeats(type);
        const transmission = vehicle.transmission || vehicle.transmissionType || getDefaultTransmission(type);
        const fuel = vehicle.fuel || vehicle.fuelType || "Fuel";
        const rate = vehicle.rentalRate || vehicle.rate || vehicle.price || "0";

        card.innerHTML = `
            <div class="car-image-area">
                <img
                    src="${imageSrc}"
                    alt="${escapeHtml(title)}"
                    onerror="this.src='https://via.placeholder.com/900x560?text=DriveEase+Vehicle'"
                >
            </div>

            <div class="car-card-body">
                <h3>${escapeHtml(title)}</h3>

                <div class="car-divider"></div>

                <div class="car-bottom">
                    <div class="car-specs">
                        <span class="spec-pill">
                            <span class="spec-icon">▣</span>
                            ${escapeHtml(formatType(type))}
                        </span>

                        <span class="spec-pill">
                            <span class="spec-icon">⚙</span>
                            ${escapeHtml(transmission)}
                        </span>

                        <span class="spec-pill">
                            <span class="spec-icon">♙</span>
                            ${escapeHtml(seats)} seats
                        </span>

                        <span class="spec-pill">
                            <span class="spec-icon">⛽</span>
                            ${escapeHtml(fuel)}
                        </span>
                    </div>

                    <div class="car-price">
                        <strong>Rs. ${escapeHtml(rate)}</strong>
                        <span>/Day</span>
                    </div>
                </div>

                <button class="book-now-btn" type="button">
                    Book Now
                </button>
            </div>
        `;

        const bookButton = card.querySelector(".book-now-btn");

        bookButton.addEventListener("click", () => {
            if (!vehicleId) {
                alert("Vehicle ID missing. Please check vehicle data.");
                return;
            }

            window.location.href = `/bookVehicle?id=${encodeURIComponent(vehicleId)}`;
        });

        grid.appendChild(card);
    });
}

function formatType(type) {
    const value = String(type).toUpperCase();

    if (value === "CAR") return "Car";
    if (value === "SUV") return "SUV";
    if (value === "VAN") return "Van";
    if (value === "MOTORCYCLE") return "Motorcycle";

    return type;
}

function getDefaultSeats(type) {
    const value = String(type).toUpperCase();

    if (value === "MOTORCYCLE") return "2";
    if (value === "VAN") return "8";
    if (value === "SUV") return "5";

    return "5";
}

function getDefaultTransmission(type) {
    const value = String(type).toUpperCase();

    if (value === "MOTORCYCLE") return "Manual";

    return "Automatic";
}

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}