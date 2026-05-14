const paymentDetails = document.getElementById('payment-details');
const paymentType = document.getElementById('paymentType');
const checkoutForm = document.getElementById('checkout-form');
const messageDiv = document.getElementById('message');

console.log('checkout.js version 20260505-2 loaded');

function renderPaymentFields(type) {
    let html = '';
    if (type === 'creditCard') {
        html = `
            <div class="form-group">
                <label for="cardNumber">Card Number</label>
                <input id="cardNumber" name="cardNumber" type="text" inputmode="numeric" maxlength="19" required>
            </div>
            <div class="form-group">
                <label for="cardHolder">Card Holder</label>
                <input id="cardHolder" name="cardHolder" type="text" required>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label for="expiryDate">Expiry Date (MM/YY)</label>
                    <input id="expiryDate" name="expiryDate" type="text" placeholder="MM/YY" maxlength="5" required>
                </div>
                <div class="form-group">
                    <label for="cvv">CVV</label>
                    <input id="cvv" name="cvv" type="text" inputmode="numeric" maxlength="4" required>
                </div>
            </div>`;
    } else if (type === 'paypal') {
        html = `
            <div class="form-group">
                <label for="paypalEmail">PayPal Email</label>
                <input id="paypalEmail" name="paypalEmail" type="email" required>
            </div>`;
    } else {
        html = `
            <div class="form-group">
                <label for="receiptNumber">Receipt Number</label>
                <input id="receiptNumber" name="receiptNumber" type="text" required>
            </div>`;
    }
    paymentDetails.innerHTML = html;
}

function showMessage(text, type = 'success') {
    messageDiv.textContent = text;
    messageDiv.className = `message visible ${type}`;
}

function buildPaymentMethod(type) {
    if (type === 'creditCard') {
        return {
            type: 'creditCard',
            cardNumber: document.getElementById('cardNumber').value.trim(),
            cardHolder: document.getElementById('cardHolder').value.trim(),
            expiryDate: document.getElementById('expiryDate').value.trim(),
            cvv: document.getElementById('cvv').value.trim()
        };
    }
    if (type === 'paypal') {
        return {
            type: 'paypal',
            paypalEmail: document.getElementById('paypalEmail').value.trim()
        };
    }
    return {
        type: 'cash',
        receiptNumber: document.getElementById('receiptNumber').value.trim()
    };
}

function validateForm() {
    const rentalDays = Number(document.getElementById('rentalDays').value);
    const amountPerDay = Number(document.getElementById('amountPerDay').value);
    const discount = Number(document.getElementById('discount').value);
    const lateFee = Number(document.getElementById('lateFee').value);
    const customerName = document.getElementById('customerName').value.trim();
    const vehicleId = document.getElementById('vehicleId').value.trim();
    const vehicleName = document.getElementById('vehicleName').value.trim();

    if (!customerName || !vehicleId || !vehicleName) {
        throw new Error('Customer name, vehicle ID, and vehicle model are required.');
    }
    if (!Number.isInteger(rentalDays) || rentalDays <= 0) {
        throw new Error('Rental days must be a whole number greater than 0.');
    }
    if (amountPerDay <= 0) {
        throw new Error('Amount per day must be greater than 0.');
    }
    if (discount < 0) {
        throw new Error('Discount cannot be negative.');
    }
    if (lateFee < 0) {
        throw new Error('Late fee cannot be negative.');
    }
    const type = paymentType.value;
    const paymentMethod = buildPaymentMethod(type);
    if (type === 'creditCard') {
        if (!/^\d{13,19}$/.test(paymentMethod.cardNumber)) {
            throw new Error('Credit card number must be 13 to 19 digits.');
        }
        if (!paymentMethod.cardHolder) {
            throw new Error('Card holder name is required.');
        }
        if (!/^(0[1-9]|1[0-2])\/\d{2}$/.test(paymentMethod.expiryDate)) {
            throw new Error('Expiry date must use MM/YY format.');
        }
        if (!/^\d{3,4}$/.test(paymentMethod.cvv)) {
            throw new Error('CVV must be 3 or 4 digits.');
        }
    }
    if (type === 'paypal') {
        if (!/^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(paymentMethod.paypalEmail)) {
            throw new Error('Enter a valid PayPal email.');
        }
    }
    if (type === 'cash' && !paymentMethod.receiptNumber) {
        throw new Error('Receipt number is required for cash payments.');
    }
    return paymentMethod;
}

paymentType.addEventListener('change', () => renderPaymentFields(paymentType.value));
window.addEventListener('load', () => renderPaymentFields(paymentType.value));

checkoutForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    messageDiv.className = 'message';
    try {
        const paymentMethod = validateForm();
        const body = {
            customerName: document.getElementById('customerName').value.trim(),
            vehicleId: document.getElementById('vehicleId').value.trim(),
            vehicleName: document.getElementById('vehicleName').value.trim(),
            rentalDays: Number(document.getElementById('rentalDays').value),
            amountPerDay: Number(document.getElementById('amountPerDay').value),
            discount: Number(document.getElementById('discount').value),
            lateFee: Number(document.getElementById('lateFee').value),
            paymentMethod
        };

        console.log('Checkout form values:', {
            customerName: document.getElementById('customerName').value,
            vehicleId: document.getElementById('vehicleId').value,
            vehicleName: document.getElementById('vehicleName').value,
            rentalDays: document.getElementById('rentalDays').value,
            amountPerDay: document.getElementById('amountPerDay').value,
            discount: document.getElementById('discount').value,
            lateFee: document.getElementById('lateFee').value,
            paymentType: paymentType.value
        });
        console.log('Vehicle ID input element found:', !!document.getElementById('vehicleId'));
        console.log('Sending invoice payload:', body);
        console.log('Sending invoice payload JSON:', JSON.stringify(body));

        const response = await fetch('/api/invoices', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });

        if (!response.ok) {
            const errorText = await readErrorMessage(response);
            throw new Error(errorText || 'Unable to create invoice.');
        }

        const invoice = await response.json();

        const transactionId = document.getElementById('transactionId').value;

        if (transactionId && transactionId !== "Unknown") {
            await fetch(`/markAsPaid?transactionId=${transactionId}`, { method: 'POST' });
        }

        showMessage(`Payment successful! Invoice #${invoice.id} created. Redirecting...`, 'success')

        setTimeout(() => {
                    window.location.href = '/reservationHistory';
        }, 2000);

        checkoutForm.reset();
        renderPaymentFields(paymentType.value);
    } catch (error) {
        showMessage(error.message || 'Validation failed.', 'error');
    }
});

async function readErrorMessage(response) {
    const text = await response.text();

    try {
        const error = JSON.parse(text);
        if (Array.isArray(error.errors) && error.errors.length > 0) {
            return error.errors.map(item => `${item.field}: ${item.message}`).join(', ');
        }
        return error.message || text;
    } catch {
        return text;
    }
}
