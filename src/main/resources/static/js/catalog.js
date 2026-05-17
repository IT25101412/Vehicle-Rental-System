window.onload = () => {
    setupCategoryTabs();
    setupLoadMore();
    loadCatalog();
};

let allVehicles = [];
let activeType = "ALL";
let visibleCount = 4;
const pageSize = 4;

function setupCategoryTabs() {
    const tabs = document.querySelectorAll(".category-tab");

    tabs.forEach(tab => {
        tab.addEventListener("click", () => {
            tabs.forEach(item => item.classList.remove("active"));
            tab.classList.add("active");

            activeType = tab.dataset.type;
            visibleCount = 4;

            renderVehicles();
        });
    });
}

function setupLoadMore() {
    const button = document.getElementById("loadMoreBtn");

    if (!button) return;

    button.addEventListener("click", () => {
        visibleCount += pageSize;
        renderVehicles();
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

console.log("Vehicles API response:", data);

allVehicles = Array.isArray(data)
    ? data
    : data.vehicles || data.data || data.content || [];

console.log("Vehicles used by catalog:", allVehicles);

renderVehicles();

    } catch (error) {
        console.error(error);

        grid.innerHTML = `
            <div class="empty-state">
                <h3>Could not load vehicles</h3>
                <p>Check if your backend API <strong>/api/vehicles</strong> is running.</p>
            </div>
        `;

        const loadMoreBtn = document.getElementById("loadMoreBtn");
        if (loadMoreBtn) loadMoreBtn.classList.add("hidden");
    }
}

function renderVehicles() {
    const grid = document.getElementById("catalogGrid");
    const loadMoreBtn = document.getElementById("loadMoreBtn");

    const filtered = allVehicles.filter(vehicle => {
    const availabilityValue =
        vehicle.available ??
        vehicle.isAvailable ??
        vehicle.availability ??
        vehicle.status;

    const isAvailable =
        availabilityValue === true ||
        availabilityValue === 1 ||
        String(availabilityValue).toLowerCase() === "true" ||
        String(availabilityValue).toLowerCase() === "available";

    const vehicleType =
        vehicle.type ||
        vehicle.vehicleType ||
        vehicle.category;

    const matchesType =
        activeType === "ALL" ||
        String(vehicleType).toUpperCase() === activeType;

    return matchesType;
});

    const visibleVehicles = filtered.slice(0, visibleCount);

    grid.innerHTML = "";

    if (visibleVehicles.length === 0) {
        grid.innerHTML = `
            <div class="empty-state">
                <h3>No vehicles available</h3>
                <p>No vehicles are available in this category right now.</p>
            </div>
        `;

        if (loadMoreBtn) loadMoreBtn.classList.add("hidden");
        return;
    }

    visibleVehicles.forEach(vehicle => {
        const card = document.createElement("a");
        card.className = "car-card";
        card.href = `bookVehicle?id=${encodeURIComponent(vehicle.vehicleId)}`;

        const imageFile = vehicle.vehicleImageFileName || "";
        const imageSrc = imageFile
            ? `/images/${imageFile}`
            : "https://via.placeholder.com/800x520?text=Vehicle";

        const seats = vehicle.seats || vehicle.seatCount || vehicle.capacity || "4";
        const transmission = vehicle.transmission || vehicle.transmissionType || "Automatic";
        const bags = vehicle.bags || vehicle.bagCount || vehicle.luggage || "1";
        const title = `${vehicle.make || ""} ${vehicle.model || ""}`.trim() || "Vehicle";

        card.innerHTML = `
            <div class="car-image-area">
                <img 
                    src="${imageSrc}" 
                    alt="${escapeHtml(title)}"
                    onerror="this.src='https://via.placeholder.com/800x520?text=Vehicle'"
                >
            </div>

            <div class="car-card-body">
                <h3>${escapeHtml(title)}</h3>

                <div class="car-divider"></div>

                <div class="car-bottom">
                    <div class="car-specs">
                        <span class="spec-pill">
                            <span class="spec-icon">♙</span>
                            ${escapeHtml(seats)}
                        </span>

                        <span class="spec-pill">
                            <span class="spec-icon">⌘</span>
                            ${escapeHtml(transmission)}
                        </span>

                        <span class="spec-pill">
                            <span class="spec-icon">▣</span>
                            ${escapeHtml(bags)} bags
                        </span>
                    </div>

                    <div class="car-price">
                        <strong>Rs. ${escapeHtml(vehicle.rentalRate || "0")}</strong>
                        <span>/Day</span>
                    </div>
                </div>
            </div>
        `;

        grid.appendChild(card);
    });

    if (loadMoreBtn) {
        if (visibleCount >= filtered.length) {
            loadMoreBtn.classList.add("hidden");
        } else {
            loadMoreBtn.classList.remove("hidden");
        }
    }
}

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}