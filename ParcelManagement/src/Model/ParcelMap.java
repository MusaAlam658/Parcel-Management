package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParcelMap {
    private Map<String, Parcel> parcelMap;
    private Log log;

    public ParcelMap(Log log) {
        this.parcelMap = new HashMap<>();
        this.log = log;
    }

    public void addParcel(Parcel parcel) {
        // Calculate the parcel fee
        double fee = calculateFee(parcel);
        parcelMap.put(parcel.getId(), parcel);

        log.addLog("Added parcel with ID: " + parcel.getId() + 
                   " with default status: " + parcel.getStatus() + 
                   ", Fee: " + parcel.getFee() + 
                   ", Discount: " + parcel.getDiscount() + 
                   ", Total Price: " + parcel.getTotalPrice());
    }



    // Search parcel
    public Parcel getParcelById(String parcelId) {
        Parcel parcel = parcelMap.get(parcelId);
        if (parcel != null) {
            System.out.println("Retrieved parcel with ID: " + parcelId);
        } else {
            System.out.println("Parcel with ID " + parcelId + " not found.");
        }
        return parcel;
    }

    // Delete a parcel
    public Parcel removeParcel(String parcelId) {
        Parcel removedParcel = parcelMap.remove(parcelId);
        if (removedParcel != null) {
            System.out.println("Removed parcel with ID: " + parcelId);
        } else {
            System.out.println("Error: Parcel with ID '" + parcelId + "' does not exist.");
        }
        return removedParcel;
    }
    
    

    // Load parcels from CSV
    public void loadParcelsFromCSV(String filePath) {
        System.out.println("Starting to read CSV file: " + filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    try {
                        String parcelId = data[0];
                        int daysInDepot = Integer.parseInt(data[1]);
                        double weight = Double.parseDouble(data[2]);
                        double length = Double.parseDouble(data[3]);
                        double width = Double.parseDouble(data[4]);
                        double height = Double.parseDouble(data[5]);

                        Parcel parcel = new Parcel(parcelId, daysInDepot, weight, length, width, height);

                        addParcel(parcel);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing numeric values in line: " + line + " (" + e.getMessage() + ")");
                    }
                } else {
                    System.out.println("Invalid data format (missing fields): " + line);
                }
            }
            System.out.println("Finished reading CSV file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading parcels from file: " + e.getMessage());
        }
    }

    // Print the parcel list
    public void printParcelMap() {
        if (parcelMap.isEmpty()) {
            System.out.println("No parcels loaded.");
            System.out.println("No parcels loaded.");
        } else {
            System.out.println("Parcels in the map:");
            System.out.println("Parcels in the map:");
            for (Map.Entry<String, Parcel> entry : parcelMap.entrySet()) {
                String parcelId = entry.getKey();
                Parcel parcel = entry.getValue();
                System.out.println("Parcel ID: " + parcelId + " , " + parcel);
            }
        }
    }
    
 // Add this method in the ParcelMap class
    public void printCollectedParcels() {
        if (parcelMap.isEmpty()) {
            System.out.println("No parcels loaded.");
        } else {
            System.out.println("Collected Parcels:");
            for (Map.Entry<String, Parcel> entry : parcelMap.entrySet()) {
                String parcelId = entry.getKey();
                Parcel parcel = entry.getValue();
                if ("COLLECTED".equalsIgnoreCase(parcel.getStatus())) {
                    System.out.println("Parcel ID: " + parcelId + " , " + parcel);
                }
            }
        }
    }





    //Calculate the parcel Fee
    public double calculateFee(Parcel parcel) {
        double baseFee = 10.0; // Base fee 
        double weightFee = parcel.getWeight() * 2.0;
        double totalFee = baseFee + weightFee;

        // Add late fee if the parcel is in depot for more than 7 days
        if (parcel.getDaysInDepot() > 7) {
            double lateFee = (parcel.getDaysInDepot() - 7) * 5.0;
            totalFee += lateFee;
            System.out.println("Late fee applied for Parcel ID: " + parcel.getId() + 
                       " -> Days in Depot: " + parcel.getDaysInDepot() + 
                       ", Late Fee: " + lateFee);
        }

        // $5 discount if the parcel ID ends with "5"
        double discount = 0.0;
        if (parcel.getId().endsWith("5")) {
            discount = 5.0;
            totalFee -= discount;
            System.out.println("Discount applied for Parcel ID: " + parcel.getId() + 
                       " -> Discount: " + discount);
        }

        // Calculate total price (fee - discount)
        double totalPrice = totalFee;

        parcel.setFee(totalFee);
        parcel.setDiscount(discount);
        parcel.setTotalPrice(totalPrice);

        System.out.println("Calculated fee for Parcel ID: " + parcel.getId() + 
                   " -> Base Fee: " + baseFee + ", Weight Fee: " + weightFee + 
                   ", Discount: " + discount + ", Total Price: " + totalPrice);
        return totalFee;
    }

}
