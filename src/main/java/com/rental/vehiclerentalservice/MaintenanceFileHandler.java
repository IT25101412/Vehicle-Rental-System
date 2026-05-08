package com.rental.vehiclerentalservice;

import java.io.*;
import java.util.*;

public class MaintenanceFileHandler {

    private static final String FILE_PATH = "maintenance.txt";

    // READ — Load all records from file
    public static List<MaintenanceRecord> loadAllRecords() {
        List<MaintenanceRecord> records = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return records;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);
                if (parts.length == 6) {
                    records.add(new MaintenanceRecord(
                            parts[0], parts[1], parts[2],
                            parts[3], parts[4], parts[5]
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    // WRITE — Save all records back to file
    public static void saveAllRecords(List<MaintenanceRecord> records) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (MaintenanceRecord r : records) {
                writer.write(r.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CREATE — Add a new record
    public static void addRecord(MaintenanceRecord record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(record.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // DELETE — Remove a record by recordId
    public static void deleteRecord(String recordId) {
        List<MaintenanceRecord> records = loadAllRecords();
        records.removeIf(r -> r.getRecordId().equals(recordId));
        saveAllRecords(records);
    }

    // UPDATE — Update the status of a record
    public static void updateStatus(String recordId, String newStatus) {
        List<MaintenanceRecord> records = loadAllRecords();
        for (MaintenanceRecord r : records) {
            if (r.getRecordId().equals(recordId)) {
                r.setStatus(newStatus);
                break;
            }
        }
        saveAllRecords(records);
    }
}