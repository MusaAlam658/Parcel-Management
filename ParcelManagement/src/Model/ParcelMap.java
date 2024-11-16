package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParcelMap {
    private Map<String, Parcel> parcelMap;
    private Log log; // Log instance for logging actions

    // Constructor to initialize ParcelMap and Log instances
    public ParcelMap(Log log) {
        this.parcelMap = new HashMap<>();
        this.log = log; // Passing Log instance to ParcelMap
    }

    // Method to add a parcel to the map and log the action
    public void addParcel(Parcel parcel) {
        parcelMap.put(parcel.getId(), parcel);
        log.addLog("Added parcel with ID: " + parcel.getId());
    }

    // Method to retrieve a parcel by ID and log the action
    public Parcel getParcelById(String parcelId) {
        Parcel parcel = parcelMap.get(parcelId);
        if (parcel != null) {
            log.addLog("Retrieved parcel with ID: " + parcelId);
        } else {
            log.addLog("Parcel with ID " + parcelId + " not found.");
        }
        return parcel;
    }

    // Method to load parcels from a CSV file with logging
    public void loadParcelsFromCSV(String filePath) {
        log.addLog("Starting to read CSV file: " + filePath); // Log the start of CSV reading
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip the header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Split on commas to properly parse CSV format
                if (data.length >= 6) {
                    try {
                        String parcelId = data[0];
                        int daysInDepot = Integer.parseInt(data[1]);
                        double weight = Double.parseDouble(data[2]);
                        double length = Double.parseDouble(data[3]); // Length
                        double width = Double.parseDouble(data[4]); // Width
                        double height = Double.parseDouble(data[5]); // Height

                        // Create a new Parcel object
                        Parcel parcel = new Parcel(parcelId, daysInDepot, weight, length, width, height);

                        // Add it to the map
                        addParcel(parcel);
                    } catch (NumberFormatException e) {
                        log.addLog("Error parsing numeric values in line: " + line + " (" + e.getMessage() + ")");
                    }
                } else {
                    log.addLog("Invalid data format (missing fields): " + line); // Log invalid data format
                }
            }
            log.addLog("Finished reading CSV file: " + filePath); // Log the end of file reading
        } catch (IOException e) {
            log.addLog("Error reading parcels from file: " + e.getMessage()); // Log IO exceptions
        } catch (NumberFormatException e) {
            log.addLog("Error parsing numeric values: " + e.getMessage()); // Log number format exceptions
        }
    }

    // Method to print all parcels in the map with logging
    public void printParcelMap() {
        if (parcelMap.isEmpty()) {
            log.addLog("No parcels loaded."); // Log when no parcels are loaded
            System.out.println("No parcels loaded.");
        } else {
            log.addLog("Parcels in the map:");
            System.out.println("Parcels in the map:");
            for (Map.Entry<String, Parcel> entry : parcelMap.entrySet()) {
                log.addLog("Parcel ID: " + entry.getKey() + " -> " + entry.getValue()); // Log each parcel
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
        }
    }
}