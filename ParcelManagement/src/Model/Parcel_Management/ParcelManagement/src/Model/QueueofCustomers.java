package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QueueofCustomers {
    private Queue<Customer> queue;
    private Log log;  // Log instance for logging actions

    public QueueofCustomers(Log log) {
        this.queue = new LinkedList<>();
        this.log = log;  // Pass the Log instance
    }

    public void addCustomer(Customer customer) {
        queue.add(customer);
        log.addLog("Added customer with ID: " + customer.getId());
    }

    public Customer processCustomer() {
        Customer customer = queue.poll();  // Removes and returns the first customer in the queue
        if (customer != null) {
            log.addLog("Processing customer with ID: " + customer.getId());
        }
        return customer;
    }

    // Method to load customers from a CSV file, sort them by surname, and assign sequence numbers
    public void loadCustomersFromCSV(String filePath) {
        log.addLog("Starting to read CSV file: " + filePath);  // Log the start of CSV reading

        List<Customer> customerList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();  // Skip the header line

            int seqNo = 1;  // Sequence number for customers
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");  // Split on comma
                if (data.length >= 2) {
                    String customerName = data[0].trim();
                    String customerId = data[1].trim();

                    // Create a new Customer object and assign a sequence number
                    Customer customer = new Customer(customerName, customerId, seqNo++);
                    customerList.add(customer);
                    log.addLog("Loaded customer: " + customerName + " (ID: " + customerId + ")");
                } else {
                    log.addLog("Invalid data format: " + line);  // Log invalid data format
                }
            }

            // Sort the customers by surname (assuming surname is the first part of the name)
            Collections.sort(customerList, new Comparator<Customer>() {
                @Override
                public int compare(Customer c1, Customer c2) {
                    String surname1 = c1.getName().split(" ")[1];  // Assuming name is "First Last"
                    String surname2 = c2.getName().split(" ")[1];  // Assuming name is "First Last"
                    return surname1.compareTo(surname2);
                }
            });

            // Add customers to the queue
            for (Customer customer : customerList) {
                addCustomer(customer);
            }

            log.addLog("Finished reading CSV file: " + filePath);  // Log the end of file reading
        } catch (IOException e) {
            log.addLog("Error reading customers from file: " + e.getMessage());  // Log IO exceptions
        }
    }

    // Method to print all customers in the queue with logging
    public void printQueue() {
        if (queue.isEmpty()) {
            log.addLog("No customers in the queue.");
            System.out.println("No customers in the queue.");
        } else {
            System.out.println("Customers in the queue:");
            for (Customer customer : queue) {
                log.addLog("Customer ID: " + customer.getId() + " -> " + customer.getName() + " (Seq No: " + customer.getSeqNo() + ")");
                System.out.println(customer.getId() + " -> " + customer.getName() + " (Seq No: " + customer.getSeqNo() + ")");
            }
        }
    }
}