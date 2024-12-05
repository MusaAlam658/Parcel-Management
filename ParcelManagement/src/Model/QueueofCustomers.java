package Model;

import java.io.*;
import java.util.*;

public class QueueofCustomers {
    private Queue<Customer> queue;
    private Log log;
    private static final String CSV_FILE_PATH = "src/View/Customers.csv";

    public QueueofCustomers(Log log) {
        this.queue = new LinkedList<>();
        this.log = log;
    }

    //Method to add the customer
    //Sequence number increases by 1
    public void addCustomer(Customer customer) {
    	int seqNo = queue.size() + 1;
        customer.setSeqNo(seqNo);
        queue.add(customer);

        System.out.println("Added customer: " + customer);

        // Sort the queue by surname
        sortQueueBySurname();
    }
    
    //Sorts the Queue by the surname
    public void sortQueueBySurname() {
        List<Customer> customerList = new ArrayList<>(queue);

        customerList.sort(Comparator.comparing(c -> extractSurname(c.getName())));

        queue.clear();
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            customer.setSeqNo(i + 1);
            queue.add(customer);
        }

        System.out.println("Queue sorted by customer surnames.");
    }


    //Gives an error if there are no customers in the list
    public Customer processCustomer() {
        Customer customer = queue.poll();
        if (customer != null) {
            System.out.println("Processing customer: " + customer);
        } else {
        	System.out.println("No customers to process.");
        }
        return customer;
    }

    // Load customers from CSV into the queue
    public void loadCustomersFromCSV() {
        System.out.println("Starting to load customers from: " + CSV_FILE_PATH);
        List<Customer> customerList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            br.readLine();
            int seqNo = 1;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    String customerName = data[0].trim();
                    String customerId = data[1].trim();

                    Customer customer = new Customer(customerName, customerId, seqNo++);
                    customerList.add(customer);
                    System.out.println("Loaded customer: " + customer);
                } else {
                    System.out.println("Invalid data format in CSV line: " + line);
                }
            }

            customerList.sort(Comparator.comparing(c -> extractSurname(c.getName())));

            //adding customers
            for (Customer customer : customerList) {
                addCustomer(customer);
            }

            System.out.println("Finished loading customers.");
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    //Extracting the surname to be able to sort 
    private String extractSurname(String name) {
        String[] parts = name.split(" ");
        return parts.length > 1 ? parts[1] : parts[0];
    }

    // Delete a customer
    public void deleteCustomerBySeqNo(int seqNo) {
        boolean found = false;
        List<Customer> updatedList = new ArrayList<>();
        for (Customer customer : queue) {
            if (customer.getSeqNo() != seqNo) {
                updatedList.add(customer);
            } else {
                found = true;
                System.out.println("Deleted customer: " + customer);
            }
        }

        if (found) {
            queue.clear();
            queue.addAll(updatedList);
            saveCustomersToCSV(updatedList); 
            log.addLog("Customer with Sequence Number " + seqNo + " has been deleted.");
        } else {
            System.out.println("Customer with Sequence Number " + seqNo + " not found.");
        }
    }

    // Save updated customer list back to CSV
    private void saveCustomersToCSV(List<Customer> updatedList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            writer.write("Name,ID,SeqNo\n");
            for (Customer customer : updatedList) {
                writer.write(customer.getName() + "," + customer.getId() + "," + customer.getSeqNo() + "\n");
            }
            System.out.println("Updated customers saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving customers to CSV: " + e.getMessage());
        }
    }

    // Print all customers in the queue
    public void printQueue() {
        if (queue.isEmpty()) {
            System.out.println("No customers in the queue.");
            System.out.println("No customers in the queue.");
        } else {
            System.out.println("Customers in the queue:");
            for (Customer customer : queue) {
                System.out.println(customer);
//                System.out.println("Customer in queue: " + customer);
            }
        }
    }
    
    //Get the Seq number for the retrieval of the parcel
    public Customer getCustomerBySeqNo(int seqNo) {
        for (Customer customer : queue) {
            if (customer.getSeqNo() == seqNo) {
                return customer;
            }
        }
        return null; // Return null if customer with given seqNo is not found
    }

    
    
}
