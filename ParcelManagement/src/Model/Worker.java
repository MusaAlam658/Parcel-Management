package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Worker {
    private Log log;

    public Worker(Log log) {
        this.log = log;
    }

    // Add a new parcel to the CSV file and ParcelMap
    public void addParcel(Parcel parcel, String csvFilePath, ParcelMap parcelMap) {
        try (FileWriter writer = new FileWriter(csvFilePath, true)) {
            writer.write(parcel.getId() + "," +
                         parcel.getDaysInDepot() + "," +
                         parcel.getWeight() + "," +
                         parcel.getLength() + "," +
                         parcel.getWidth() + "," +
                         parcel.getHeight() + "\n");

            // Add parcel to ParcelMap
            parcelMap.addParcel(parcel);

            log.addLog("Successfully added parcel with ID: " + parcel.getId());
            System.out.println("Parcel added successfully.");
        } catch (IOException e) {
            System.out.println("Failed to add parcel: " + e.getMessage());
            System.out.println("Error: Could not add parcel.");
        }
    }
    
    public String getValidParcelId(Scanner scanner) {
        String parcelId;
        while (true) {
            System.out.print("Enter Parcel ID (format: X###): ");
            parcelId = scanner.nextLine();

            if (parcelId.matches("X\\d{3}")) {
                break;
            } else {
                System.out.println("Invalid Parcel ID. Please try again.");
            }
        }
        return parcelId;
    }
    
    public boolean isParcelIdInFile(String parcelId, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); 
                if (data.length > 0 && data[0].trim().equals(parcelId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return false; 
    }
    
    public void appendCustomerToCSV(Customer customer, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String customerData = customer.getName() + "," + customer.getId() + "," + customer.getSeqNo();
            writer.write(customerData);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to customer file: " + e.getMessage());
        }
    }


    // Delete a parcel
    public void deleteParcelById(String parcelId, ParcelMap parcelMap) {
        Parcel removedParcel = parcelMap.removeParcel(parcelId); 
        if (removedParcel != null) {
            System.out.println("Successfully deleted parcel with ID: " + parcelId);
            System.out.println("Parcel with ID: " + parcelId + " has been deleted.");
        } else {
            System.out.println("Parcel with ID: " + parcelId + " not found.");
            System.out.println("Parcel with ID: " + parcelId + " not found.");
        }
    }
    
 // Search for a parcel
    public Parcel searchParcelById(String parcelId, ParcelMap parcelMap) {
        Parcel parcel = parcelMap.getParcelById(parcelId); // Look up the parcel in the ParcelMap
        if (parcel != null) {
            System.out.println("Parcel found: " + parcelId);
            System.out.println("Parcel found: " + parcel);
            return parcel;
        } else {
            System.out.println("Parcel with ID: " + parcelId + " not found.");
            System.out.println("Parcel with ID: " + parcelId + " not found.");
            return null;
        }
    }

    
    public void addCustomer(Customer customer, QueueofCustomers customerQueue) {
        customerQueue.addCustomer(customer);
        System.out.println("Worker added customer: " + customer);
    }
}

