//package Model;
//
//import java.util.Scanner;
//
//public class Log {
//    private StringBuffer log;
//
//    public Log() {
//        log = new StringBuffer();
//    }
//
//    public void addLog(String message) {
//        log.append(message).append("\n");
//    }
//
//    public String getLog() {
//        return log.toString();
//    }
//
//    public static void main(String[] args) {
//        Log log = new Log();
//        ParcelMap parcelMap = new ParcelMap(log);
//        Worker worker = new Worker(log);
//
//        // Load data
//        parcelMap.loadParcelsFromCSV("src/View/Parcels.csv");
//        QueueofCustomers queueOfCustomers = new QueueofCustomers(log);
//        queueOfCustomers.loadCustomersFromCSV();
//
//        Scanner scanner = new Scanner(System.in);
//        int choice = 0;
//
//        do {
//            System.out.println("\n==== MENU ====");
//            System.out.println("1: View Parcel Map");
//            System.out.println("2: View Customer Queue");
//            System.out.println("3: Add a Parcel");
//            System.out.println("4: Delete a Parcel");
//            System.out.println("5: Delete a Customer");
//            System.out.println("6: Add a Customer");
//            System.out.println("0: Exit");
//            System.out.print("Enter your choice: ");
//
//            try {
//                choice = scanner.nextInt();
//            } catch (Exception e) {
//                System.out.println("Invalid input. Please enter a number.");
//                scanner.nextLine();
//                continue;
//            }
//
//            switch (choice) {
//                case 1:
//                    System.out.println("Reading from the parcel csv...");
//                    log.addLog("Viewing Parcel Map.");
//                    parcelMap.printParcelMap();
//                    break;
//
//                case 2:
//                    System.out.println("Reading from the customer csv...");
//                    System.out.println("Sorting by Customer surname...");
//                    log.addLog("Viewing Customer Queue.");
//                    queueOfCustomers.printQueue();
//                    break;
//                    
//                case 3:
//                    scanner.nextLine();
//
//                    // Validate and get Parcel ID
//                    String parcelId = worker.getValidParcelId(scanner);
//
//                    // Prompt for other parcel details
//                    System.out.print("Enter Days in Depot: ");
//                    int daysInDepot = scanner.nextInt();
//
//                    System.out.print("Enter Weight: ");
//                    double weight = scanner.nextDouble();
//
//                    System.out.print("Enter Length: ");
//                    double length = scanner.nextDouble();
//
//                    System.out.print("Enter Width: ");
//                    double width = scanner.nextDouble();
//
//                    System.out.print("Enter Height: ");
//                    double height = scanner.nextDouble();
//
//                    // Create and add a new Parcel
//                    Parcel newParcel = new Parcel(parcelId, daysInDepot, weight, length, width, height);
//                    worker.addParcel(newParcel, "src/View/Parcels.csv", parcelMap);
//
//                    log.addLog("Added Parcel: " + parcelId);
//
//                    break;
//
//
//                case 4:
//                    scanner.nextLine();
//                    System.out.print("Enter Parcel ID to delete: ");
//                    String delParcelId = scanner.nextLine();
//                    worker.deleteParcelById(delParcelId, parcelMap);
//                    log.addLog("Deleted Parcel: " + delParcelId);
//                    break;
//
//                case 5:
//                    System.out.print("Enter Sequence Number of customer to delete: ");
//                    int seqNo = scanner.nextInt();
//                    queueOfCustomers.deleteCustomerBySeqNo(seqNo);
//                    log.addLog("Deleted Customer with Sequence Number: " + seqNo);
//                    break;
//
//                case 6:
//                    scanner.nextLine();
//                    System.out.print("Enter Customer Name: ");
//                    String name = scanner.nextLine();
//                    System.out.print("Enter Parcel ID: ");
//                    String id = scanner.nextLine();
//
//                    Customer newCustomer = new Customer(name, id, 0);
//                    worker.addCustomer(newCustomer, queueOfCustomers);
//                    log.addLog("Added Customer: " + name + " (ID: " + id + ")");
//                    break;
//
//                case 0:
//                    System.out.println("Exiting...");
//                    log.addLog("User exited the program.");
//                    break;
//
//                default:
//                    System.out.println("Invalid choice. Try again.");
//            }
//        } while (choice != 0);
//
//        System.out.println("\n==== Log Output ====");
//        System.out.println(log.getLog());
//    }
//}




package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    // The static instance of Log (singleton)
    private static Log instance;

    // Relative path to the logs file inside the View folder
    private static final String LOG_FILE_PATH = "src/View/log.txt";

    // DateTime formatter to format the timestamp
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Public method to get the single instance of Log
    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    // Method to log a message into the log file
    public void addLog(String message) {
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String logMessage = String.format("[%s] %s", timestamp, message);

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.println(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to clear the log file when the Manager GUI is accessed
    public void clearLogFile() {
        // Open the file in overwrite mode (false means "do not append")
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, false))) {
            // Opening the PrintWriter in 'false' mode clears the file contents automatically
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
