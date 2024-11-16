package Model;

public class Log {
    private StringBuffer log;

    public Log() {
        log = new StringBuffer();
    }

    public void addLog(String message) {
        log.append(message).append("\n");
    }

    public String getLog() {
        return log.toString();
    }

    public static void main(String[] args) {
        Log log = new Log();

        // Example for ParcelMap
        ParcelMap parcelMap = new ParcelMap(log);
        parcelMap.loadParcelsFromCSV("src/View/Parcels.csv");
        System.out.println("Reading from the file...");
//        parcelMap.printParcelMap();
        System.out.println("The Parcel Map has been made");

        // Example for QueueofCustomers
        QueueofCustomers queueOfCustomers = new QueueofCustomers(log);
        queueOfCustomers.loadCustomersFromCSV("src/View/Customers.csv");
        System.out.println("Reading from the file...");
//        queueOfCustomers.printQueue();
        System.out.println("The Customer Queue has been made");



//        System.out.println("Log Output:");
//        System.out.println(log.getLog());
        System.out.println("The Customer Queue has been sorted according to the surnames");

    }
}
