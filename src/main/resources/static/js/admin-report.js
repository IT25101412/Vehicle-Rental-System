const reportMessage = document.getElementById('reportMessage');
const invoiceTableContainer = document.getElementById('invoiceTableContainer');
const summaryContainer = document.getElementById('summaryContainer');
const refreshButton = document.getElementById('refreshButton');

function showMessage(text, type = 'success') {
    reportMessage.textContent = text;
    reportMessage.className = `message visible ${type}`;
}

function createSummary(invoices) {
    if (!summaryContainer) {
        return;
    }

    if (!invoices.length) {
        summaryContainer.innerHTML = '<div class="summary-empty">No invoices to display yet.</div>';
        return;
    }

    const paidCount = invoices.filter(i => i.status === 'PAID').length;
    const pendingCount = invoices.filter(i => i.status === 'PENDING').length;
    const cancelledCount = invoices.filter(i => i.status === 'CANCELLED').length;
    const totalRevenue = invoices
        .filter(i => i.status === 'PAID')
        .reduce((sum, invoice) => sum + invoice.totalAmount, 0);
    const outstanding = invoices
        .filter(i => i.status === 'PENDING')
        .reduce((sum, invoice) => sum + invoice.totalAmount, 0);

    summaryContainer.innerHTML = `
        <div class="summary-grid">
            <div class="summary-card">
                <span>Total Invoices</span>
                <strong>${invoices.length}</strong>
            </div>
            <div class="summary-card">
                <span>Paid</span>
                <strong>${paidCount}</strong>
            </div>
            <div class="summary-card">
                <span>Pending</span>
                <strong>${pendingCount}</strong>
            </div>
            <div class="summary-card">
                <span>Cancelled</span>
                <strong>${cancelledCount}</strong>
            </div>
            <div class="summary-card accent">
                <span>Total Revenue</span>
                <strong>${formatCurrency(totalRevenue)}</strong>
            </div>
            <div class="summary-card accent-soft">
                <span>Outstanding Balance</span>
                <strong>${formatCurrency(outstanding)}</strong>
            </div>
        </div>`;
}

function formatCurrency(value) {
    return `$${Number(value).toFixed(2)}`;
}

function createTable(invoices) {
    if (invoices.length === 0) {
        invoiceTableContainer.innerHTML = '<div class="card"><p>No invoices have been created yet.</p></div>';
        return;
    }

    let rows = invoices.map(invoice => {
        return `
            <tr>
                <td>${invoice.id}</td>
                <td>${invoice.customerName}</td>
                <td>${invoice.vehicleName}</td>
                <td>${invoice.rentalDays}</td>
                <td>${formatCurrency(invoice.amountPerDay)}</td>
                <td>${formatCurrency(invoice.subtotal)}</td>
                <td>${formatCurrency(invoice.discount)}</td>
                <td>${formatCurrency(invoice.lateFee)}</td>
                <td>${formatCurrency(invoice.totalAmount)}</td>
                <td><span class="status-pill ${invoice.status}">${invoice.status}</span></td>
                <td>${invoice.paymentMethod.type}</td>
                <td>
                    <div class="action-buttons">
                        <button class="button" onclick="markPaid(${invoice.id})">Mark Paid</button>
                        <button class="button secondary" onclick="showEditForm(${invoice.id})">Update</button>
                        <button class="button secondary" onclick="deleteInvoice(${invoice.id})">Delete</button>
                    </div>
                </td>
            </tr>`;
    }).join('');

    invoiceTableContainer.innerHTML = `
        <div class="card">
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Customer</th>
                            <th>Vehicle</th>
                            <th>Days</th>
                            <th>Rate</th>
                            <th>Subtotal</th>
                            <th>Discount</th>
                            <th>Late Fee</th>
                            <th>Total</th>
                            <th>Status</th>
                            <th>Payment</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>${rows}</tbody>
                </table>
            </div>
        </div>
        <div id="editFormContainer"></div>`;
}

async function loadInvoices() {
    reportMessage.className = 'message';
    try {
        const response = await fetch('/api/invoices');
        const invoices = await response.json();
        createSummary(invoices);
        createTable(invoices);
    } catch (error) {
        showMessage('Unable to load invoices. Please refresh.', 'error');
    }
}

async function markPaid(id) {
    const body = { status: 'PAID' };
    try {
        const response = await fetch(`/api/invoices/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        if (!response.ok) {
            const text = await response.text();
            throw new Error(text || 'Unable to mark invoice paid.');
        }
        showMessage(`Invoice #${id} marked as paid.`);
        await loadInvoices();
    } catch (error) {
        showMessage(error.message, 'error');
    }
}

function showEditForm(id) {
    const container = document.getElementById('editFormContainer');
    container.innerHTML = `
        <div class="card">
            <h2>Update Invoice #${id}</h2>
            <div class="form-row">
                <div class="form-group">
                    <label for="updateDiscount">Discount</label>
                    <input id="updateDiscount" type="number" min="0" step="0.01" value="0">
                </div>
                <div class="form-group">
                    <label for="updateLateFee">Late Fee</label>
                    <input id="updateLateFee" type="number" min="0" step="0.01" value="0">
                </div>
                <div class="form-group">
                    <label for="updateStatus">Status</label>
                    <select id="updateStatus">
                        <option value="PENDING">PENDING</option>
                        <option value="PAID">PAID</option>
                        <option value="CANCELLED">CANCELLED</option>
                    </select>
                </div>
            </div>
            <div class="button-row">
                <button class="button" onclick="updateInvoice(${id})">Save Changes</button>
                <button class="button secondary" onclick="clearEditForm()">Cancel</button>
            </div>
        </div>`;
}

function clearEditForm() {
    const container = document.getElementById('editFormContainer');
    container.innerHTML = '';
}

async function updateInvoice(id) {
    const discount = Number(document.getElementById('updateDiscount').value);
    const lateFee = Number(document.getElementById('updateLateFee').value);
    const status = document.getElementById('updateStatus').value;

    if (discount < 0 || lateFee < 0) {
        showMessage('Discount and late fee must be zero or greater.', 'error');
        return;
    }

    const body = { discount, lateFee, status };
    try {
        const response = await fetch(`/api/invoices/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        if (!response.ok) {
            const text = await response.text();
            throw new Error(text || 'Unable to update invoice.');
        }
        showMessage(`Invoice #${id} updated successfully.`);
        clearEditForm();
        await loadInvoices();
    } catch (error) {
        showMessage(error.message, 'error');
    }
}

async function deleteInvoice(id) {
    if (!confirm(`Delete invoice #${id}? This cannot be undone.`)) {
        return;
    }
    try {
        const response = await fetch(`/api/invoices/${id}`, { method: 'DELETE' });
        if (!response.ok) {
            const text = await response.text();
            throw new Error(text || 'Unable to delete invoice.');
        }
        showMessage(`Invoice #${id} deleted successfully.`);
        await loadInvoices();
    } catch (error) {
        showMessage(error.message, 'error');
    }
}

refreshButton.addEventListener('click', loadInvoices);
window.addEventListener('load', loadInvoices);
