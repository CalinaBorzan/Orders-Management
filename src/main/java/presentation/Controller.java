package presentation;

import business.ClientBL;
import business.OrderBL;
import business.ProductBL;
import data.BillDAO;
import model.Bill;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
/**
 * Controller for managing GUI interactions and delegating business logic operations.
 * It handles user actions from the GUI and coordinates with the business layer to perform CRUD operations
 * on clients, products, and orders, as well as managing bills.
 */
public class Controller {
    private View view;
    private ClientBL clientBL;
    private ProductBL productBL;
    private OrderBL orderBL;
    /**
     * Constructs a Controller object with associated business logic handlers and a view.
     *
     * @param view The GUI frame handling user interaction.
     * @param clientBL The business logic class for client operations.
     * @param productBL The business logic class for product operations.
     * @param orderBL The business logic class for order operations.
     */
    public Controller(View view, ClientBL clientBL, ProductBL productBL, OrderBL orderBL) {
        this.view = view;
        this.clientBL = clientBL;
        this.productBL = productBL;
        this.orderBL = orderBL;
        attachButtonListeners();
        initController();
    }
    /**
     * Attaches event listeners to buttons in the view.
     */
    private void attachButtonListeners() {
        view.addClientButton.addActionListener(e -> addClient());
        view.editClientButton.addActionListener(e -> editClient());
        view.deleteClientButton.addActionListener(e -> deleteClient());
        view.addProductButton.addActionListener(e -> addProduct());
        view.editProductButton.addActionListener(e -> editProduct());
        view.deleteProductButton.addActionListener(e -> deleteProduct());
        view.addOrderButton.addActionListener(e -> addOrder());
        view.getViewBillsButton().addActionListener(e -> viewBills());
    }
    /**
     * Initializes the controller by refreshing table data and setting initial visibility of frames.
     */
    private void initController() {
        refreshTableData();
        view.initializeComboBoxes(clientBL.listAllClients(), productBL.listAllProducts());

        view.clientFrame.setVisible(true);
        view.productFrame.setVisible(true);
        view.orderFrame.setVisible(true);
    }
    /**
     * Refreshes the data displayed in the tables of the view.
     */
    private void refreshTableData() {
        view.setupTablePanel(clientBL.listAllClients(), "Clients", view.getClientTable());
        view.setupTablePanel(productBL.listAllProducts(), "Products", view.getProductTable());
        view.setupTablePanel(orderBL.listAllOrders(), "Orders", view.getOrderTable());
    }
    /**
     * Handles adding a new client by displaying input dialogs to gather client data and then
     * invoking the business logic to add the client.
     */
    private void addClient() {
        String lastName = JOptionPane.showInputDialog(view.getClientFrame(), "Enter Client Last Name:");
        String firstName = JOptionPane.showInputDialog(view.getClientFrame(), "Enter Client First Name:");
        String email = JOptionPane.showInputDialog(view.getClientFrame(), "Enter Client Email:");
        try {
            int age = Integer.parseInt(JOptionPane.showInputDialog(view.getClientFrame(), "Enter Client Age:"));
            String address = JOptionPane.showInputDialog(view.getClientFrame(), "Enter Client Address:");
            Client client = new Client(0, lastName, firstName, email, age, address);
            clientBL.addClient(client);
            refreshTableData();
            JOptionPane.showMessageDialog(view.getClientFrame(), "Client added successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getClientFrame(), "Invalid age entered. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles editing an existing client by displaying dialogs to update client information and
     * invoking the business logic to update the client data.
     */
    private void editClient() {
        int selectedRow = view.getClientTable().getSelectedRow();
        if (selectedRow >= 0) {
            int clientId = (Integer) view.getClientTable().getValueAt(selectedRow, 0);
            Client client = clientBL.findById(clientId);
            if (client != null) {
                String lastName = JOptionPane.showInputDialog(view.getClientFrame(), "Enter new Last Name:", client.getLastName());
                String firstName = JOptionPane.showInputDialog(view.getClientFrame(), "Enter new First Name:", client.getFirstName());
                String email = JOptionPane.showInputDialog(view.getClientFrame(), "Enter new Email:", client.getEmail());
                try {
                    int age = Integer.parseInt(JOptionPane.showInputDialog(view.getClientFrame(), "Enter new Age:", client.getAge()));
                    String address = JOptionPane.showInputDialog(view.getClientFrame(), "Enter new Address:", client.getAddress());
                    client.setLastName(lastName);
                    client.setFirstName(firstName);
                    client.setEmail(email);
                    client.setAge(age);
                    client.setAddress(address);
                    clientBL.editClient(client);
                    refreshTableData();
                    JOptionPane.showMessageDialog(view.getClientFrame(), "Client updated successfully!");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view.getClientFrame(), "Invalid age entered. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(view.getClientFrame(), "No client found with ID: " + clientId, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(view.getClientFrame(), "Please select a client to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles deleting a selected client by invoking business logic and updating the GUI accordingly.
     */
    private void deleteClient() {
        int selectedRow = view.getClientTable().getSelectedRow();
        if (selectedRow >= 0) {
            int clientId = (Integer) view.getClientTable().getValueAt(selectedRow, 0);
            try {
                if (clientBL.deleteClient(clientId)) {
                    refreshTableData();
                    JOptionPane.showMessageDialog(view.getClientFrame(), "Client deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(view.getClientFrame(), "Failed to delete client.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                JOptionPane.showMessageDialog(view.getClientFrame(), "Cannot delete client: the record is referenced by other tables.", "Constraint Violation", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view.getClientFrame(), "An error occurred while trying to delete the client.", "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(view.getClientFrame(), "Please select a client to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles adding a new product similar to addClient.
     */
    private void addProduct() {
        String name = JOptionPane.showInputDialog(view.getProductFrame(), "Enter Product Name:");
        try {
            double price = Double.parseDouble(JOptionPane.showInputDialog(view.getProductFrame(), "Enter Price:"));
            int quantity = Integer.parseInt(JOptionPane.showInputDialog(view.getProductFrame(), "Enter Quantity:"));
            Product product = new Product(0, name, quantity, price);
            productBL.addProduct(product);
            refreshTableData();
            JOptionPane.showMessageDialog(view.getProductFrame(), "Product added successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getProductFrame(), "Invalid number entered. Please enter valid numbers for price and quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles editing an existing product similar to editClient.
     */
    private void editProduct() {
        int selectedRow = view.getProductTable().getSelectedRow();
        if (selectedRow >= 0) {
            int productId = (Integer) view.getProductTable().getValueAt(selectedRow, 0);
            Product product = productBL.findById(productId);
            if (product != null) {
                String name = JOptionPane.showInputDialog(view.getProductFrame(), "Enter new Product Name:", product.getProductName());
                try {
                    double price = Double.parseDouble(JOptionPane.showInputDialog(view.getProductFrame(), "Enter new Price:", product.getPrice()));
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(view.getProductFrame(), "Enter new Quantity:", product.getQuantity()));
                    product.setProductName(name);
                    product.setPrice(price);
                    product.setQuantity(quantity);
                    productBL.editProduct(product);
                    refreshTableData();
                    JOptionPane.showMessageDialog(view.getProductFrame(), "Product updated successfully!");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view.getProductFrame(), "Invalid number entered. Please enter valid numbers for price and quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(view.getProductFrame(), "No product found with ID: " + productId, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(view.getProductFrame(), "Please select a product to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles deleting a selected product similar to deleteClient.
     */
    private void deleteProduct() {
        int selectedRow = view.getProductTable().getSelectedRow();
        if (selectedRow >= 0) {
            int productId = (Integer) view.getProductTable().getValueAt(selectedRow, 0);
            try {
                if (productBL.deleteProduct(productId)) {
                    refreshTableData();
                    JOptionPane.showMessageDialog(view.getProductFrame(), "Product deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(view.getProductFrame(), "Failed to delete product.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                JOptionPane.showMessageDialog(view.getProductFrame(), "Cannot delete product: the record is referenced by other tables.", "Constraint Violation", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view.getProductFrame(), "An error occurred while trying to delete the product.", "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(view.getProductFrame(), "Please select a product to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles the creation of a new order by collecting input from the user and using business logic to create the order.
     */
    private void addOrder() {
        try {
            int clientId = Integer.parseInt(JOptionPane.showInputDialog(view.getOrderFrame(), "Enter Client ID:"));
            int productId = Integer.parseInt(JOptionPane.showInputDialog(view.getOrderFrame(), "Enter Product ID:"));
            int quantity = Integer.parseInt(JOptionPane.showInputDialog(view.getOrderFrame(), "Enter Quantity:"));

            if (quantity <= 0) {
                JOptionPane.showMessageDialog(view.getOrderFrame(), "Quantity must be greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Client client = clientBL.findById(clientId);
            Product product = productBL.findById(productId);

            if (client == null) {
                JOptionPane.showMessageDialog(view.getOrderFrame(), "Client not found.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (product == null) {
                JOptionPane.showMessageDialog(view.getOrderFrame(), "Product not found.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Order newOrder = new Order(0, clientId, productId, new Date(), quantity);
            if (orderBL.createOrder(newOrder.getOrderId(), client, product, newOrder.getQuantity())) {
                refreshTableData();
                JOptionPane.showMessageDialog(view.getOrderFrame(), "Order added successfully.");
            } else {
                JOptionPane.showMessageDialog(view.getOrderFrame(), "Failed to create order.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getOrderFrame(), "Invalid number entered. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view.getOrderFrame(), "Error creating order: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getOrderFrame(), "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Displays all bills in a new frame using a table.
     */
    private void viewBills() {
        try {
            List<Bill> bills = orderBL.getAllBills();
            displayBills(bills);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view.getOrderFrame(), "An error occurred while fetching the bills.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    /**
     * Displays all bills in a new frame using a table.
     * This method creates a new window that lists all bills in a JTable, allowing the user to view detailed information
     * about each bill. Each bill's data is shown in a structured format with columns representing the bill's properties.
     *
     * @param bills the list of Bill objects to be displayed in the table.
     */
    private void displayBills(List<Bill> bills) {
        JFrame billFrame = new JFrame("All Bills");
        billFrame.setSize(600, 400);
        billFrame.setLocationRelativeTo(null);

        JTable billTable = new JTable();
        view.initializeTableWithReflection(bills, billTable);

        billFrame.add(new JScrollPane(billTable));
        billFrame.setVisible(true);
    }

}
