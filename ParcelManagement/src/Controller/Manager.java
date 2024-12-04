package Controller;

import Model.Customer;
import Model.Log;
import Model.Parcel;
import Model.ParcelMap;
import Model.QueueofCustomers;
import Model.Worker;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Manager {
	
    public static void main(String[] args) {
        // Initialize necessary objects
        Log log = Log.getInstance();  // This will get the same Log instance every time
        log.clearLogFile(); // Clear the log file

        ParcelMap parcelMap = new ParcelMap(log);
        QueueofCustomers queueOfCustomers = new QueueofCustomers(log);
        Worker worker = new Worker(log);

        // Load data
        parcelMap.loadParcelsFromCSV("src/View/Parcels.csv");
        queueOfCustomers.loadCustomersFromCSV();

        // Main frame setup
        JFrame frame = new JFrame("Manager Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Buttons for main dashboard
        JButton customerViewButton = new JButton("Customer View");
        JButton parcelViewButton = new JButton("Parcel View");

        // Set button fonts and sizes
        customerViewButton.setFont(new Font("Arial", Font.PLAIN, 20));
        parcelViewButton.setFont(new Font("Arial", Font.PLAIN, 20));
        customerViewButton.setPreferredSize(new Dimension(250, 60));
        parcelViewButton.setPreferredSize(new Dimension(250, 60));

        // Label for the dashboard
        JLabel displayLabel = new JLabel("Welcome to Manager Dashboard", SwingConstants.CENTER);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        // Button panel setup
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(customerViewButton);
        buttonPanel.add(parcelViewButton);

        // Add components to the main frame
        frame.setLayout(new BorderLayout());
        frame.add(displayLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Display the main frame
        frame.setVisible(true);
        
     // Customer View button action
        customerViewButton.addActionListener(e -> {
            System.out.println("Entering the Customer View");
            JFrame customerFrame = new JFrame("Customer View");
            customerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            customerFrame.setSize(800, 600);

            // Capture the customer list output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(baos);
            PrintStream originalSystemOut = System.out;
            System.setOut(printStream);

            // Print customer queue to the captured stream
            log.addLog("Printing the Customer Queue");
            queueOfCustomers.printQueue();
            log.addLog("Printed the Customer Queue");

            // Restore original System.out
            System.setOut(originalSystemOut);

            // Get the printed output
            String customerList = baos.toString();
            JTextArea customerArea = new JTextArea(15, 40);
            customerArea.setText(customerList);
            customerArea.setEditable(false); // Make it non-editable
            JScrollPane scrollPane = new JScrollPane(customerArea);

            // Create buttons for adding and deleting customers
            JButton addCustomerButton = new JButton("Add Customer");
            JButton deleteCustomerButton = new JButton("Delete Customer");
            JButton RefreshButton = new JButton("Refresh the List");
            JButton backButton = new JButton("Back to Main Panel");
            JButton ReceiveParcelButton = new JButton("Receive Parcel");
         
            // Set button font
            addCustomerButton.setFont(new Font("Arial", Font.PLAIN, 16));
            deleteCustomerButton.setFont(new Font("Arial", Font.PLAIN, 16));
            RefreshButton.setFont(new Font("Arial", Font.PLAIN, 16));
            backButton.setFont(new Font("Arial", Font.PLAIN, 16));
            ReceiveParcelButton.setFont(new Font("Arial", Font.PLAIN, 16));

            ReceiveParcelButton.addActionListener(e4 -> {
                // Get the customer with sequence number 1
                Customer customer = queueOfCustomers.getCustomerBySeqNo(1);  // assuming seq 1 for the demo
                if (customer != null) {
                    // Retrieve the parcel associated with the customer
                    Parcel parcel = parcelMap.getParcelById(customer.getId());

                    if (parcel != null) {
                        // Create a new JFrame for displaying the parcel collection info
                        JFrame parcelFrame = new JFrame("Parcel Collection Confirmation");
                        parcelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        parcelFrame.setSize(500, 400);
                        parcelFrame.setLayout(new BorderLayout());

                        // Construct the message to display in the dialog
                        String message = "Sequence number: " + customer.getSeqNo() + "\n" +
                                         "Name of the customer: " + customer.getName() + "\n" +
                                         "Is the user here to collect the parcel: " + parcel.getId() + "\n" +
                                         "Total: " + parcel.getTotalPrice() + "\n" +
                                         "Would the customer like to receive their parcel?";

                        // Create a label to display the message
                        JLabel label = new JLabel("<html>" + message.replace("\n", "<br>") + "</html>", SwingConstants.CENTER);
                        label.setFont(new Font("Arial", Font.PLAIN, 14));
                        parcelFrame.add(label, BorderLayout.CENTER);

                        // Create buttons for Yes and No
                        JPanel buttonPanelForYesNo = new JPanel();
                        JButton yesButton = new JButton("Yes");
                        JButton noButton = new JButton("No");

                     // Yes button action - change parcel status and remove customer
                        yesButton.addActionListener(e6 -> {
                            // Change the parcel status to "COLLECTED"
                            parcel.setStatus("COLLECTED");

                            // Remove the customer from the queue
                            queueOfCustomers.deleteCustomerBySeqNo(1);

                            // Notify the user and close the frame
                            JOptionPane.showMessageDialog(parcelFrame, "You have confirmed to collect the parcel.");
                            parcelFrame.dispose(); // Close the confirmation frame
                        });

                        // No button action - simply close the frame
                        noButton.addActionListener(e6 -> {
                            parcelFrame.dispose(); // Close the frame without doing anything
                        });

                        // Add buttons to the panel
                        buttonPanelForYesNo.add(yesButton);
                        buttonPanelForYesNo.add(noButton);


                        // Add the button panel to the frame
                        parcelFrame.add(buttonPanelForYesNo, BorderLayout.SOUTH);  // This will only show the Yes/No buttons

                        // Display the frame
                        parcelFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "No parcel found for the customer.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "No customer found with sequence number 1.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

                   
            backButton.addActionListener(e4 -> {
                customerFrame.dispose(); // Close the Customer View window
                frame.setVisible(true);  // Show the Main Dashboard again
            });

       

         // Add Customer Button action
            addCustomerButton.addActionListener(e1 -> {
                JFrame addCustomerFrame = new JFrame("Add Customer");
                addCustomerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addCustomerFrame.setSize(400, 500);

                // Create a form panel with input fields for customer details
                JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
                JLabel nameLabel = new JLabel("Customer Name:");
                JTextField nameField = new JTextField();
                JLabel parcelIdLabel = new JLabel("Parcel ID:");
                JTextField parcelIdField = new JTextField();

                // Add fields to the form panel
                formPanel.add(nameLabel);
                formPanel.add(nameField);
                formPanel.add(parcelIdLabel);
                formPanel.add(parcelIdField);

                // Create Add button and define its action
                JButton addButton = new JButton("Add");
                addButton.addActionListener(e2 -> {
                	System.out.println("Adding a Customer");
                    try {
                        // Input and validate the values entered for adding a parcel
                        String name = nameField.getText().trim();
                        String parcelId = parcelIdField.getText().trim();

                        if (name.isEmpty() || parcelId.isEmpty()) {
                            JOptionPane.showMessageDialog(
                                addCustomerFrame,
                                "Please fill in both fields.",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                        
                     // Name should only include letters
                        if (!name.matches("[a-zA-Z ]+")) {
                            JOptionPane.showMessageDialog(
                                addCustomerFrame,
                                "Invalid Name. Only letters and spaces are allowed.",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        // Validate Parcel ID to make sure it exists
                        boolean parcelExists = worker.isParcelIdInFile(parcelId, "src/View/Parcels.csv");
                        if (!parcelExists) {
                            JOptionPane.showMessageDialog(
                                addCustomerFrame,
                                "The Parcel ID does not exist in the parcel file. Please enter a valid Parcel ID.",
                                "Invalid Parcel ID",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        // Create and add the customer
                        Customer newCustomer = new Customer(name, parcelId, 0); // Sequence auto-handled
                        System.out.println("Adding new Customer");
                        worker.addCustomer(newCustomer, queueOfCustomers);

                        // Append customer details to the CSV file
                        worker.appendCustomerToCSV(newCustomer, "src/View/Customers.csv");
                        System.out.println("Added Customer to the CSV file");

                        // Log and notify the user
                        log.addLog("Added Customer: " + name + " (ID: " + parcelId + ")");
                        JOptionPane.showMessageDialog(addCustomerFrame, "Customer added successfully.");
                        System.out.println("Added the Customer in the list");

                        // Clear fields after successful addition
                        nameField.setText("");
                        parcelIdField.setText("");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                            addCustomerFrame,
                            "Error adding customer: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                });

                // Add the buttons
                formPanel.add(addButton);
                addCustomerFrame.add(formPanel);
                addCustomerFrame.setVisible(true);
            });



            // Delete Customer Button action
            deleteCustomerButton.addActionListener(deleteEvent -> {
            	System.out.println("Deleting a Customer from the list");
                // Prompt user to enter Customer Sequence Number to delete
                String seqNoString = JOptionPane.showInputDialog(customerFrame, "Enter the Customer Sequence Number to delete:");
                System.out.println("Validating the user entry");

                try {
                    if (seqNoString != null && !seqNoString.trim().isEmpty()) {
                        int seqNo = Integer.parseInt(seqNoString);
                        // Delete customer by sequence number
                        queueOfCustomers.deleteCustomerBySeqNo(seqNo);
                        
                        JOptionPane.showMessageDialog(customerFrame, "Customer deleted successfully.");
                        System.out.println("Deleted the Customer");
                    } else {
                        JOptionPane.showMessageDialog(customerFrame, "Customer Sequence Number is invalid or empty.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(customerFrame, "Invalid Sequence Number format.");
                }
            });
            
            RefreshButton.addActionListener(e3 -> {
            	log.addLog("Refreshes the Customer List");
                // Capture the customer list output again after refreshing
                ByteArrayOutputStream baosRefresh = new ByteArrayOutputStream();
                PrintStream printStreamRefresh = new PrintStream(baosRefresh);
                System.setOut(printStreamRefresh);
                // Print customer queue
                queueOfCustomers.printQueue();
                System.setOut(originalSystemOut);
                String refreshedCustomerList = baosRefresh.toString();
                customerArea.setText(refreshedCustomerList);
            });
            // Panel setup for buttons and scroll
            JPanel actionPanel = new JPanel();
            actionPanel.add(addCustomerButton);
            actionPanel.add(deleteCustomerButton);
            actionPanel.add(RefreshButton);
            actionPanel.add(backButton);
            actionPanel.add(ReceiveParcelButton);


            
            // Add components to customer frame
            customerFrame.add(actionPanel, BorderLayout.NORTH);
            customerFrame.add(scrollPane, BorderLayout.CENTER);
            customerFrame.setVisible(true);
        });



        
        // Parcel View button action
        parcelViewButton.addActionListener(e -> {
            JFrame parcelFrame = new JFrame("Parcel View");
            System.out.println("Entering the Parcel View");
            parcelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            parcelFrame.setSize(1000, 600);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(baos);
            PrintStream originalSystemOut = System.out;
            System.setOut(printStream);

            log.addLog("Print the Parcel Map");
            parcelMap.printParcelMap();
            System.setOut(originalSystemOut);

            // Display the parcel map 
            String parcelMapList = baos.toString();
            JTextArea parcelMapArea = new JTextArea(15, 40);
            parcelMapArea.setText(parcelMapList);
            parcelMapArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(parcelMapArea);

            // Buttons for the Parcel View
            JButton searchParcelButton = new JButton("Search Parcel");
            JButton addParcelButton = new JButton("Add Parcel");
            JButton deleteParcelButton = new JButton("Delete Parcel");
            JButton RefreshButton = new JButton("Refresh the List");
            JButton WaitingListButton = new JButton("Waiting List");
            JButton backButton = new JButton("Back to Main Panel");

            
            Font buttonFont = new Font("Arial", Font.PLAIN, 16);
            searchParcelButton.setFont(buttonFont);
            addParcelButton.setFont(buttonFont);
            deleteParcelButton.setFont(buttonFont);
            RefreshButton.setFont(buttonFont);
            backButton.setFont(new Font("Arial", Font.PLAIN, 16));
            WaitingListButton.setFont(new Font("Arial", Font.PLAIN, 16));


            
            // Add buttons to panel
            JPanel parcelButtonPanel = new JPanel(new FlowLayout());
            parcelButtonPanel.add(searchParcelButton);
            parcelButtonPanel.add(addParcelButton);
            parcelButtonPanel.add(deleteParcelButton);
            parcelButtonPanel.add(RefreshButton);
            parcelButtonPanel.add(backButton);
            parcelButtonPanel.add(WaitingListButton);

            
            parcelFrame.setLayout(new BorderLayout());
            parcelFrame.add(scrollPane, BorderLayout.CENTER);
            parcelFrame.add(parcelButtonPanel, BorderLayout.NORTH);

            searchParcelButton.addActionListener(searchEvent -> {
            	System.out.println("Searching for Parcel in List...");
                // Prompt user for Parcel ID to search for
                String parcelIdToSearch = JOptionPane.showInputDialog(parcelFrame, "Enter the Parcel ID to search for:");

                if (parcelIdToSearch != null && !parcelIdToSearch.trim().isEmpty()) {
                    // Search for the parcel in ParcelMap
                    Parcel foundParcel = parcelMap.getParcelById(parcelIdToSearch);
                    if (foundParcel != null) {
                        JOptionPane.showMessageDialog(parcelFrame, "Parcel Found: " + foundParcel);
                    } else {
                        JOptionPane.showMessageDialog(parcelFrame, "Parcel not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(parcelFrame, "Parcel ID is invalid or empty.");
                }
            });
            
            
            
            WaitingListButton.addActionListener(e3 -> {
                JFrame collectedFrame = new JFrame("Collected Parcels");
                System.out.println("Displaying Collected Parcels");
                collectedFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                collectedFrame.setSize(900, 500);

                // Redirect System.out to a ByteArrayOutputStream
                ByteArrayOutputStream baoss = new ByteArrayOutputStream();
                PrintStream printStreamm = new PrintStream(baoss);
                PrintStream originalSystemOutt = System.out;
                System.setOut(printStreamm);

                
                // Log and print the collected parcels
                log.addLog("Print the Collected Parcel List");
                parcelMap.printCollectedParcels(); // Method to print parcels with COLLECTED status
                System.setOut(originalSystemOutt);

                // Display the collected parcels in a JTextArea
                String collectedParcelsList = baoss.toString();
                JTextArea collectedParcelsArea = new JTextArea(15, 40);
                collectedParcelsArea.setText(collectedParcelsList);
                collectedParcelsArea.setEditable(false);
                JScrollPane scrollPanee = new JScrollPane(collectedParcelsArea);

                // Add the JTextArea (scrollPanee) to the new frame
                collectedFrame.setLayout(new BorderLayout());
                collectedFrame.add(scrollPanee, BorderLayout.CENTER);

                collectedFrame.setVisible(true);
            });



            
            
            backButton.addActionListener(e4 -> {
                parcelFrame.dispose(); // Close the Parcel View frame
                frame.setVisible(true); // Show the main frame
            });


            
            // Add Parcel
            addParcelButton.addActionListener(e1 -> {
                JFrame addParcelFrame = new JFrame("Add Parcel");
                addParcelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addParcelFrame.setSize(400, 500);

                // Create a form panel for inputs
                JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
                JLabel parcelIdLabel = new JLabel("Parcel ID:");
                JTextField parcelIdField = new JTextField();
                JLabel daysInDepotLabel = new JLabel("Days in Depot:");
                JTextField daysInDepotField = new JTextField();
                JLabel weightLabel = new JLabel("Weight (kg):");
                JTextField weightField = new JTextField();
                JLabel lengthLabel = new JLabel("Length (cm):");
                JTextField lengthField = new JTextField();
                JLabel widthLabel = new JLabel("Width (cm):");
                JTextField widthField = new JTextField();
                JLabel heightLabel = new JLabel("Height (cm):");
                JTextField heightField = new JTextField();

                // Add fields to the panel
                formPanel.add(parcelIdLabel);
                formPanel.add(parcelIdField);
                formPanel.add(daysInDepotLabel);
                formPanel.add(daysInDepotField);
                formPanel.add(weightLabel);
                formPanel.add(weightField);
                formPanel.add(lengthLabel);
                formPanel.add(lengthField);
                formPanel.add(widthLabel);
                formPanel.add(widthField);
                formPanel.add(heightLabel);
                formPanel.add(heightField);

                JButton addButton = new JButton("Add");
                addButton.addActionListener(e2 -> {
                	System.out.println("Adds the parcel to the list");
                    try {
                        // Validate Parcel ID
                        String parcelId = parcelIdField.getText().trim();
                        if (!parcelId.matches("X\\d{3}")) {
                            // Show popup for invalid Parcel ID
                            JOptionPane.showMessageDialog(
                                addParcelFrame,
                                "Invalid Parcel ID. Format must be: X### (e.g., X123).",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        // Validate Days in Depot
                        String daysInDepotText = daysInDepotField.getText().trim();
                        if (!daysInDepotText.matches("\\d+")) {
                            JOptionPane.showMessageDialog(
                                addParcelFrame,
                                "Days in Depot must be a valid integer.",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        // Validate Weight
                        String weightText = weightField.getText().trim();
                        if (!weightText.matches("\\d+(\\.\\d+)?")) {
                            JOptionPane.showMessageDialog(
                                addParcelFrame,
                                "Weight must be a valid number.",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        // Validate Length
                        String lengthText = lengthField.getText().trim();
                        if (!lengthText.matches("\\d+(\\.\\d+)?")) {
                            JOptionPane.showMessageDialog(
                                addParcelFrame,
                                "Length must be a valid number.",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        // Validate Width
                        String widthText = widthField.getText().trim();
                        if (!widthText.matches("\\d+(\\.\\d+)?")) {
                            JOptionPane.showMessageDialog(
                                addParcelFrame,
                                "Width must be a valid number.",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return; 
                        }

                        // Validate Height
                        String heightText = heightField.getText().trim();
                        if (!heightText.matches("\\d+(\\.\\d+)?")) {
                            JOptionPane.showMessageDialog(
                                addParcelFrame,
                                "Height must be a valid number.",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        int daysInDepot = Integer.parseInt(daysInDepotText);
                        double weight = Double.parseDouble(weightText);
                        double length = Double.parseDouble(lengthText);
                        double width = Double.parseDouble(widthText);
                        double height = Double.parseDouble(heightText);

                        // Create and add the parcel
                        Parcel newParcel = new Parcel(parcelId, daysInDepot, weight, length, width, height);
                        worker.addParcel(newParcel, "src/View/Parcels.csv", parcelMap);
                        System.out.println("Adding Parcel...");
                        log.addLog("Parcel added: " + newParcel);
                        JOptionPane.showMessageDialog(addParcelFrame, "Parcel added successfully.");
                    } catch (Exception ex) {
                        // Catch any remaining errors (optional)
                        JOptionPane.showMessageDialog(addParcelFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                // Add button to the form
                formPanel.add(addButton);
                addParcelFrame.add(formPanel);
                addParcelFrame.setVisible(true);
            });

         // Delete Parcel
            deleteParcelButton.addActionListener(e2 -> {
                String delParcelId = JOptionPane.showInputDialog(parcelFrame, "Enter the Parcel ID to delete:");

                if (delParcelId != null) {
                    // Delete the parcel by ID
                    worker.deleteParcelById(delParcelId, parcelMap);
                
                    log.addLog("Deleted Parcel: " + delParcelId);

                    ByteArrayOutputStream baosAfterDelete = new ByteArrayOutputStream();
                    PrintStream printStreamAfterDelete = new PrintStream(baosAfterDelete);
                    PrintStream originalSystemOutAfterDelete = System.out;
                    System.setOut(printStreamAfterDelete);

                    // Print the updated parcel map
                    parcelMap.printParcelMap();

                    System.setOut(originalSystemOutAfterDelete);
                    String updatedParcelMapList = baosAfterDelete.toString();
                    parcelMapArea.setText(updatedParcelMapList);

                    JOptionPane.showMessageDialog(parcelFrame, "Parcel deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(parcelFrame, "Parcel ID is invalid or empty.");
                }
               
            });
            
            JTextArea parcelArea = new JTextArea(15, 40);
            parcelArea.setEditable(false);
            
         // Refresh button
            RefreshButton.addActionListener(e4 -> {
                ByteArrayOutputStream baosRefresh = new ByteArrayOutputStream();
                PrintStream printStreamRefresh = new PrintStream(baosRefresh);

                System.setOut(printStreamRefresh);

                // Print parcel map
                parcelMap.printParcelMap();
                System.setOut(originalSystemOut);
                String refreshedParcelList = baosRefresh.toString();
                parcelMapArea.setText(refreshedParcelList);
            });
        

           
            parcelFrame.setVisible(true);

            frame.dispose();
        });
    }
}
