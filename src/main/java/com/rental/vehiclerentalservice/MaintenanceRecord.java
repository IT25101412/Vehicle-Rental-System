package com.rental.vehiclerentalservice;

public class MaintenanceRecord {

    private String recordId;
    private String vehicleId;
    private String serviceType;
    private String serviceDate;
    private String status;
    private String notes;

    // Constructor
    public MaintenanceRecord(String recordId, String vehicleId, String serviceType,
                             String serviceDate, String status, String notes) {
        this.recordId = recordId;
        this.vehicleId = vehicleId;
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.status = status;
        this.notes = notes;
    }

    // Getters and Setters (Encapsulation)
    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getServiceDate() { return serviceDate; }
    public void setServiceDate(String serviceDate) { this.serviceDate = serviceDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // Polymorphism — override toString for different display
    @Override
    public String toString() {
        return recordId + "," + vehicleId + "," + serviceType + ","
                + serviceDate + "," + status + "," + notes;
    }

    // Polymorphism — generate alert message based on service type
    public String getMaintenanceAlert() {
        if (serviceType.equalsIgnoreCase("Engine")) {
            return "⚠️ URGENT: Engine service required for Vehicle " + vehicleId;
        } else if (serviceType.equalsIgnoreCase("Tyre")) {
            return "🔄 Tyre replacement needed for Vehicle " + vehicleId;
        } else {
            return "🔧 Routine service scheduled for Vehicle " + vehicleId;
        }
    }
}