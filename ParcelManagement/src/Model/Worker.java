package Model;

public class Worker {
    private Log log;

    public Worker(Log log) {
        this.log = log;
    }

    // Calculate fee based on the weight of the parcel
    public double calculateFee(Parcel parcel) {
        double baseFee = 10.0; // Base fee for all parcels
        double weightFee = parcel.getWeight() * 2.0; // Example: $2 per kg
        double totalFee = baseFee + weightFee;
        
        // Log the fee calculation details
        log.addLog("Calculated fee for Parcel ID: " + parcel.getId() + 
                   " -> Base Fee: " + baseFee + ", Weight Fee: " + weightFee + 
                   ", Total Fee: " + totalFee);
        return totalFee;
    }

    // Process a parcel by calculating its fee and logging details
    public void processParcel(Customer customer, ParcelMap parcelMap) {
        Parcel parcel = parcelMap.getParcelById(customer.getId());
        if (parcel != null) {
            double totalFee = calculateFee(parcel); // Calculate fee
            log.addLog("Processed " + customer.getName() + " for Parcel ID: " + 
                       customer.getId() + ". Total Fee: " + totalFee);
        } else {
            log.addLog("Parcel ID: " + customer.getId() + " not found for " + customer.getName());
        }
    }
}
