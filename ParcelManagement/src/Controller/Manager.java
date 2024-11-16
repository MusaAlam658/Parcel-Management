//package Controller;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;
//
//public class Manager {
//    private QueueofCustomers queueOfCustomers;
//    private ParcelMap parcelMap;
//    private Log log;
//    private Worker worker;
//
//    public Manager() {
//        this.queueOfCustomers = new QueueofCustomers();
//        this.parcelMap = new ParcelMap();
//        this.log = new Log();
//        this.worker = new Worker(log);
//    }
//
//    public void readParcelsFromFile(String filename) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File(filename));
//        while (scanner.hasNextLine()) {
//            String[] parcelData = scanner.nextLine().split(",");
//            String id = parcelData[0];
//            double length = Double.parseDouble(parcelData[1]);
//            double width = Double.parseDouble(parcelData[2]);
//            double height = Double.parseDouble(parcelData[3]);
//            double weight = Double.parseDouble(parcelData[4]);
//            int daysInDepot = Integer.parseInt(parcelData[5]);
//            parcelMap.addParcel(new Parcel(id, length, width, height, weight, daysInDepot));
//        }
//        scanner.close();
//    }
//
//    public void runSimulation() {
//        // Simulate customers arriving and collecting parcels
//    }
//}
