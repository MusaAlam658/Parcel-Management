package Model;

public class Log {
    private StringBuffer log;

    public Log() {
        log = new StringBuffer();
    }

    // Method to add a log message
    public void addLog(String message) {
        log.append(message).append("\n");
    }

    // Method to retrieve the complete log as a string
    public String getLog() {
        return log.toString();
    }

    // Main method to run the application for testing
    public static void main(String[] args) {
        Log log = new Log();  // Create a Log instance

        // Example for ParcelMap
        ParcelMap parcelMap = new ParcelMap(log);  // Pass the Log instance to ParcelMap
        parcelMap.loadParcelsFromCSV("src/View/Parcels.csv");  // Load parcels
        parcelMap.printParcelMap();  // Print the parcel map

        // Example for QueueofCustomers
        QueueofCustomers queueOfCustomers = new QueueofCustomers(log);  // Pass Log instance to QueueofCustomers
        queueOfCustomers.loadCustomersFromCSV("src/View/Customers.csv");  // Load customers
        queueOfCustomers.printQueue();  // Print the queue of customers

        // Output the log
        System.out.println("Log Output:");
        System.out.println(log.getLog());  // Print the accumulated log
    }
}