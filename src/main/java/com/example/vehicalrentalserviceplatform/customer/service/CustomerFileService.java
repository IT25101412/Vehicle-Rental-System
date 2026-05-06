package com.example.vehicalrentalserviceplatform.customer.service;

import com.example.vehicalrentalserviceplatform.customer.model.RegularCustomer;
import com.example.vehicalrentalserviceplatform.customer.model.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Handles all reading and writing to customer.txt
@Service
public class CustomerFileService {

    // Path to the data file
    private static final String FILE_PATH = "customer.txt";

    // Creates the file if it doesn't exist yet
    private void initFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Could not create customer.txt: " + e.getMessage());
            }
        }
    }

    // Reads all lines from customer.txt
    private List<String> readAllLines() {
        initFile();
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("customer.txt not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading customer.txt: " + e.getMessage());
        }
        return lines;
    }

    // Writes all lines back to customer.txt
    private void writeAllLines(List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to customer.txt: " + e.getMessage());
        }
    }

    // CREATE — registers a new customer
    // Returns false if username already exists
    public boolean registerCustomer(User customer) {
        if (findCustomer(customer.getUsername()) != null) {
            return false;
        }
        String line = "";
        if (customer instanceof RegularCustomer) {
            line = ((RegularCustomer) customer).toFileString();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            initFile();
            writer.write(line);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Error saving customer: " + e.getMessage());
            return false;
        }
    }

    // READ — finds a customer by username or licenseId
    public User findCustomer(String query) {
        List<String> lines = readAllLines();
        for (String line : lines) {
            String[] fields = line.split(",");
            if (fields.length >= 4) {
                if (fields[1].equalsIgnoreCase(query) ||
                        fields[3].equalsIgnoreCase(query)) {
                    return parseLineToCustomer(line);
                }
            }
        }
        return null;
    }

    // UPDATE — updates email and phone for a customer
    public boolean updateCustomer(String username, String email, String phone) {
        List<String> lines = readAllLines();
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(",");
            if (fields.length >= 6 && fields[1].equalsIgnoreCase(username)) {
                fields[4] = email;
                fields[5] = phone;
                lines.set(i, String.join(",", fields));
                found = true;
                break;
            }
        }
        if (found) {
            writeAllLines(lines);
        }
        return found;
    }

    // UPDATE — changes password after verifying current password
    public boolean changePassword(String username, String currentPassword, String newPassword) {
        // Verify current password first
        User customer = findCustomer(username);
        if (customer == null) {
            return false;
        }
        if (!customer.validatePassword(currentPassword)) {
            return false;
        }
        // Current password correct — update it
        List<String> lines = readAllLines();
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(",");
            if (fields.length >= 6 && fields[1].equalsIgnoreCase(username)) {
                fields[2] = newPassword; // password is index 2
                lines.set(i, String.join(",", fields));
                found = true;
                break;
            }
        }
        if (found) {
            writeAllLines(lines);
        }
        return found;
    }

    // LOGIN — checks username and password match
    public boolean loginCustomer(String username, String password) {
        User customer = findCustomer(username);
        if (customer == null) {
            return false;
        }
        return customer.validatePassword(password);
    }

    // HELPER — converts a line from customer.txt into a customer object
    private User parseLineToCustomer(String line) {
        String[] fields = line.split(",");
        try {
            if (fields[0].equals("CUSTOMER") && fields.length == 6) {
                return new RegularCustomer(
                        fields[1], // username
                        fields[2], // password
                        fields[3], // licenseId
                        fields[4], // email
                        fields[5]  // phone
                );
            }
        } catch (Exception e) {
            System.out.println("Error parsing line: " + e.getMessage());
        }
        return null;
    }
}