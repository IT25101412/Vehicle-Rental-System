function toggleFields() {
    const type = document.getElementById("type").value;
    const seatsGroup = document.getElementById("seatsGroup");
    const driveGroup = document.getElementById("driveTrainGroup");
    const motoGroup = document.getElementById("motoGroup");

    if (type === "MOTORCYCLE") {
        seatsGroup.style.display = "none";
        driveGroup.style.display = "none";
        motoGroup.style.display = "block";
    } else if (type === "CAR") {
        seatsGroup.style.display = "block";
        driveGroup.style.display = "none";
        motoGroup.style.display = "none";
    } else {
        seatsGroup.style.display = "block";
        driveGroup.style.display = "block";
        motoGroup.style.display = "none";
    }
}

async function submitVehicleForm() {
    const fileInput = document.getElementById('imageUpload');
    if (!fileInput.files[0]) {
        alert("Please select a vehicle image.");
        return;
    }

    const formData = new FormData();
    const type = document.getElementById("type").value;

    formData.append("type", type);
    formData.append("make", document.getElementById("make").value);
    formData.append("model", document.getElementById("model").value);
    formData.append("year", document.getElementById("year").value);
    // REMOVED: regNo append line
    formData.append("fuel", document.getElementById("fuel").value);
    formData.append("rate", document.getElementById("rate").value);
    formData.append("mileage", document.getElementById("mileage").value);
    formData.append("image", fileInput.files[0]);

    if (type === "MOTORCYCLE") {
        formData.append("motoType", document.getElementById("motoType").value);
    } else {
        formData.append("seats", document.getElementById("seats").value);
        if (type !== "CAR") {
            formData.append("driveTrain", document.getElementById("driveTrain").value);
        }
    }

    try {
        const response = await fetch('/api/vehicles/add', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            alert("Vehicle added successfully!");
            window.location.href = 'inventory.html';
        } else {
            const errorMsg = await response.text();
            alert("Failed to save: " + errorMsg);
        }
    } catch (error) {
        console.error("Error during upload:", error);
    }
}

window.onload = toggleFields;