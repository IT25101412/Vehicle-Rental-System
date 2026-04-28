package com.example.vehicalrentalserviceplatform.customer.service;

import com.example.vehicalrentalserviceplatform.customer.model.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Handles all reading and writing to customer.txt
@Service
public class CustomerFileService {

    // Path to the data file
    // File format per line: username,password,licenseId,email,phone
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
                if (!line.trim().isEmpty()) lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading customer.txt: " + e.getMessage());
        }
        return lines;
    }

    // Writes all lines back to customer.txt (overwrites)
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
            return false; // duplicate username
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            initFile();
            writer.write(customer.toFileString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Error saving customer: " + e.getMessage());
            return false;
        }
    }

    // CREATE — registers a customer who used Google sign-in
    // Password is stored as GOOGLE_AUTH — these accounts skip normal login
    // licenseId and phone are left blank — customer fills them in on profile
    public boolean registerGoogleCustomer(String googleEmail, String googleName) {
        // Build a username from the part before @ in the email
        String base = googleEmail.split("@")[0].toLowerCase().replaceAll("[^a-z0-9]", "");

        // Make sure the username is unique
        String finalUsername = base;
        int suffix = 1;
        while (findCustomer(finalUsername) != null) {
            finalUsername = base + suffix;
            suffix++;
        }

        User customer = new User(finalUsername, "GOOGLE_AUTH", "", googleEmail, "");
        return registerCustomer(customer);
    }

    // READ — finds a customer by username or licenseId
    // Returns null if not found
    public User findCustomer(String query) {
        for (String line : readAllLines()) {
            String[] f = line.split(",");
            // username = index 0, licenseId = index 2
            if (f.length >= 5) {
                if (f[0].equalsIgnoreCase(query) || f[2].equalsIgnoreCase(query)) {
                    return parseLineToUser(line);
                }
            }
        }
        return null;
    }

    // READ — finds a customer by email address
    // Used after Google login to check if account already exists
    public User findCustomerByEmail(String email) {
        for (String line : readAllLines()) {
            String[] f = line.split(",");
            // email = index 3
            if (f.length >= 5 && f[3].equalsIgnoreCase(email)) {
                return parseLineToUser(line);
            }
        }
        return null;
    }

    // UPDATE — updates email and phone for a customer
    // Returns false if customer not found
    public boolean updateCustomer(String username, String email, String phone) {
        List<String> lines = readAllLines();
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] f = lines.get(i).split(",");
            if (f.length >= 5 && f[0].equalsIgnoreCase(username)) {
                f[3] = email;   // update email  (index 3)
                f[4] = phone;   // update phone  (index 4)
                lines.set(i, String.join(",", f));
                found = true;
                break;
            }
        }
        if (found) writeAllLines(lines);
        return found;
    }

    // LOGIN — validates username and password
    // Blocks login for Google accounts (they use the Google flow only)
    public boolean loginCustomer(String username, String password) {
        User customer = findCustomer(username);
        if (customer == null) return false;
        if ("GOOGLE_AUTH".equals(customer.getPassword())) return false;
        return customer.validatePassword(password);
    }

    // HELPER — converts one comma-separated line into a User object
    private User parseLineToUser(String line) {
        String[] f = line.split(",");
        try {
            if (f.length >= 5) {
                return new User(f[0], f[1], f[2], f[3], f[4]);
            }
        } catch (Exception e) {
            System.out.println("Error parsing line: " + e.getMessage());
        }
        return null;
    }
}