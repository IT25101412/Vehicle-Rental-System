package com.rental.vehiclerentalservice;

import java.io.*;
import java.util.*;

public class MaintenanceFileHandler {

    private static final String FILE_PATH = "maintenance.txt";

    // READ - Load all records from maintenance.txt
    public static List<MaintenanceRecord> loadAllRecords() {
        List<MaintenanceRecord> records = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return records;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 7);

                if (parts.length == 7) {
                    MaintenanceRecord record = new MaintenanceRecord(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4],
                            parts[5],
                            parts[6]
                    );

                    records.add(record);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

    // WRITE - Save all records back to maintenance.txt
    public static void saveAllRecords(List<MaintenanceRecord> records) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {

            for (MaintenanceRecord record : records) {
                writer.write(record.toString());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CREATE - Add a new maintenance record
    public static void addRecord(MaintenanceRecord record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {

            writer.write(record.toString());
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // UPDATE - Update the status of a maintenance record
    public static void updateStatus(String recordId, String newStatus) {
        List<MaintenanceRecord> records = loadAllRecords();

        for (MaintenanceRecord record : records) {
            if (record.getRecordId().equals(recordId)) {
                record.setStatus(newStatus);
                break;
            }
        }

        saveAllRecords(records);
    }

    // DELETE - Delete a maintenance record by record ID
    public static void deleteRecord(String recordId) {
        List<MaintenanceRecord> records = loadAllRecords();

        records.removeIf(record -> record.getRecordId().equals(recordId));

        saveAllRecords(records);
    }

    // SEARCH - Find a single record by record ID
    public static MaintenanceRecord findRecordById(String recordId) {
        List<MaintenanceRecord> records = loadAllRecords();

        for (MaintenanceRecord record : records) {
            if (record.getRecordId().equals(recordId)) {
                return record;
            }
        }

        return null;
    }
}