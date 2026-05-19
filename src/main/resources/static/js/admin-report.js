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
                        <td colspan="7" style="text-align:center; padding: 24px; color: var(--text-secondary);">
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
                    <td style="font-family: monospace; color: var(--text-secondary);">${invoice.invoiceId || invoice.id || "N/A"}</td>
                    <td style="font-weight: 500;">${invoice.customerName || invoice.customer || "N/A"}</td>
                    <td style="font-family: monospace; color: var(--text-secondary);">${invoice.vehicleId || invoice.vehicle || "N/A"}</td>
                    <td>${invoice.amount || invoice.totalAmount || "N/A"}</td>
                    <td><span class="badge pending">${invoice.status || "N/A"}</span></td>
                    <td style="color: var(--text-secondary);">${invoice.paymentMethod || "N/A"}</td>
                    <td style="text-align: right;">
                        <button class="btn btn-secondary" onclick="alert('Invoice details feature can be added later')">
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
                    <td colspan="7" style="text-align:center; padding: 24px; color: red; color: var(--accent-red);">
                        Failed to load invoices. Check the backend API.
                    </td>
                </tr>
            `;
        });
});