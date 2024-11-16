package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class QueueofCustomers {
    private Queue<Customer> queue;
    private Log log;

    public QueueofCustomers(Log log) {
        this.queue = new LinkedList<>();
        this.log = log;
    }

    public void addCustomer(Customer customer) {
        queue.add(customer);
        log.addLog("Added customer: " + customer);
    }

    public Customer processCustomer() {
        Customer customer = queue.poll();
        if (customer != null) {
            log.addLog("Processing customer: " + customer);
        } else {
            log.addLog("No customers to process.");
        }
        return customer;
    }

    public void loadCustomersFromCSV(String filePath) {
        log.addLog("Starting to load customers from: " + filePath);

        List<Customer> customerList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            int seqNo = 1;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    String customerName = data[0].trim();
                    String customerId = data[1].trim();

                    Customer customer = new Customer(customerName, customerId, seqNo++);
                    customerList.add(customer);
                    log.addLog("Loaded customer: " + customer);
                } else {
                    log.addLog("Invalid data format in CSV line: " + line);
                }
            }

            customerList.sort((c1, c2) -> {
                String surname1 = extractSurname(c1.getName());
                String surname2 = extractSurname(c2.getName());
                return surname1.compareTo(surname2);
            });

            for (Customer customer : customerList) {
                addCustomer(customer);
            }

            log.addLog("Finished loading customers.");
        } catch (IOException e) {
            log.addLog("Error reading CSV file: " + e.getMessage());
        }
    }

    private String extractSurname(String name) {
        String[] parts = name.split(" ");
        return parts.length > 1 ? parts[1] : parts[0];
    }

    public void printQueue() {
        if (queue.isEmpty()) {
            System.out.println("No customers in the queue.");
            log.addLog("No customers in the queue.");
        } else {
            System.out.println("Customers in the queue:");
            for (Customer customer : queue) {
                System.out.println(customer);
                log.addLog("Customer in queue: " + customer);
            }
        }
    }
}
