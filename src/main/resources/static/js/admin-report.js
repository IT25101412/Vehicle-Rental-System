document.addEventListener("DOMContentLoaded", () => {
    const summaryBox = document.getElementById("invoice-summary");
    const tableBody = document.getElementById("invoice-table-body");

    fetch("/api/invoices")
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to load invoices");
            }
            return response.json();
        })
        .then(invoices => {
            tableBody.innerHTML = "";

            if (!Array.isArray(invoices) || invoices.length === 0) {
                summaryBox.textContent = "No invoice records found yet.";

                tableBody.innerHTML = `
                    <tr>
                        <td colspan="7" style="text-align:center; padding: 24px;">
                            No invoices found. Complete a checkout to generate billing records.
                        </td>
                    </tr>
                `;
                return;
            }

            summaryBox.textContent = `Total invoices: ${invoices.length}`;

            invoices.forEach(invoice => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${invoice.invoiceId || invoice.id || "N/A"}</td>
                    <td>${invoice.customerName || invoice.customer || "N/A"}</td>
                    <td>${invoice.vehicleId || invoice.vehicle || "N/A"}</td>
                    <td>${invoice.amount || invoice.totalAmount || "N/A"}</td>
                    <td>${invoice.status || "N/A"}</td>
                    <td>${invoice.paymentMethod || "N/A"}</td>
                    <td>
                        <button onclick="alert('Invoice details feature can be added later')">
                            View
                        </button>
                    </td>
                `;

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error(error);

            summaryBox.textContent = "Could not load invoice data.";

            tableBody.innerHTML = `
                <tr>
                    <td colspan="7" style="text-align:center; padding: 24px; color: red;">
                        Failed to load invoices. Check the backend API.
                    </td>
                </tr>
            `;
        });
});