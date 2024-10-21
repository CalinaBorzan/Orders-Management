package presentation;

import model.Client;
import model.Product;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;
/**
 * The {@code View} class manages the graphical user interface components
 * for handling clients, products, and orders in a desktop application.
 */
public class View {
    JFrame clientFrame;
    JFrame productFrame;
    JFrame orderFrame;
    private JTable clientTable, productTable, orderTable;
    private JPanel clientPanel, productPanel, orderPanel;
    public JButton addClientButton, editClientButton, deleteClientButton;
    public JButton addProductButton, editProductButton, deleteProductButton;
    public JButton viewBillsButton;
    public JButton addOrderButton;
    /**
     * Constructs a new View and initializes the GUI components.
     */
    public View() {
        initializeFrames();
        initializeButtons();
        initializePanels();
        addPanelsToFrames();
    }
    /**
     * Initializes the frames used in the GUI.
     */
    private void initializeFrames() {
        clientFrame = new JFrame("Clients");
        productFrame = new JFrame("Products");
        orderFrame = new JFrame("Orders");

        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        productFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        orderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        clientFrame.setSize(500, 400);
        productFrame.setSize(500, 400);
        orderFrame.setSize(500, 400);

        clientFrame.setLocation(0, 0);
        productFrame.setLocation(520, 0);
        orderFrame.setLocation(1040, 0);
    }
    /**
     * Initializes buttons with specific properties like color and text.
     */
    private void initializeButtons() {
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        addClientButton = createButton("Add Client", Color.GREEN, buttonFont);
        editClientButton = createButton("Edit Client", Color.ORANGE, buttonFont);
        deleteClientButton = createButton("Delete Client", Color.RED, buttonFont);

        addProductButton = createButton("Add Product", Color.GREEN, buttonFont);
        editProductButton = createButton("Edit Product", Color.ORANGE, buttonFont);
        deleteProductButton = createButton("Delete Product", Color.RED, buttonFont);

        addOrderButton = createButton("Add Order", Color.GREEN, buttonFont);
        viewBillsButton = createButton("View Bills", Color.BLUE, buttonFont);
    }
    /**
     * Creates a button with specified text, background color, and font.
     *
     * @param text the text to display on the button
     * @param backgroundColor the background color of the button
     * @param font the font of the text
     * @return the newly created button
     */
    private JButton createButton(String text, Color backgroundColor, Font font) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(font);
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }
    /**
     * Initializes panels with tables and button sub-panels.
     */
    private void initializePanels() {
        clientTable = new JTable();
        productTable = new JTable();
        orderTable = new JTable();

        clientPanel = createTablePanel(clientTable, "Clients");
        productPanel = createTablePanel(productTable, "Products");
        orderPanel = createOrderTablePanel(orderTable, "Orders");
    }
    /**
     * Creates a panel containing a table and associated buttons for operations.
     *
     * @param table the table to be included in the panel
     * @param title the title for the panel and buttons
     * @return the panel containing the table and buttons
     */
    private JPanel createTablePanel(JTable table, String title) {
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(null, title,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 16), Color.BLUE));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 5, 5));

        JButton addButton = new JButton("Add " + title.substring(0, title.length() - 1));
        JButton editButton = new JButton("Edit " + title.substring(0, title.length() - 1));
        JButton deleteButton = new JButton("Delete " + title.substring(0, title.length() - 1));

        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        editButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));

        switch (title) {
            case "Clients":
                addClientButton = addButton;
                editClientButton = editButton;
                deleteClientButton = deleteButton;
                break;
            case "Products":
                addProductButton = addButton;
                editProductButton = editButton;
                deleteProductButton = deleteButton;
                break;
        }

        addButton.setBackground(Color.BLUE);
        addButton.setForeground(Color.WHITE);
        addButton.setOpaque(true);
        addButton.setBorderPainted(false);

        editButton.setBackground(Color.BLUE);
        editButton.setForeground(Color.WHITE);
        editButton.setOpaque(true);
        editButton.setBorderPainted(false);

        deleteButton.setBackground(Color.BLUE);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    /**
     * Creates an order table panel specifically for managing orders.
     *
     * @param table the table for displaying orders
     * @param title the title of the panel
     * @return the panel specifically designed for orders
     */
    private JPanel createOrderTablePanel(JTable table, String title) {
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(null, title,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 16), Color.BLUE));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 5, 5));

        JButton addButton = new JButton("Add " + title.substring(0, title.length() - 1));
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.setBackground(Color.BLUE);
        addButton.setForeground(Color.WHITE);
        addButton.setOpaque(true);
        addButton.setBorderPainted(false);
        addOrderButton = addButton;

        viewBillsButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewBillsButton.setBackground(Color.BLUE);
        viewBillsButton.setForeground(Color.WHITE);
        viewBillsButton.setOpaque(true);
        viewBillsButton.setBorderPainted(false);

        buttonPanel.add(addButton);
        buttonPanel.add(viewBillsButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Adds panels to their respective frames and makes them visible.
     */
    private void addPanelsToFrames() {
        clientFrame.add(clientPanel);
        productFrame.add(productPanel);
        orderFrame.add(orderPanel);

        clientFrame.setVisible(true);
        productFrame.setVisible(true);
        orderFrame.setVisible(true);
    }
    /**
     * Sets up a table panel using a list of items. Reflects to determine column names from object fields.
     *
     * @param items the list of items to be displayed in the table
     * @param panelTitle the title of the panel
     * @param table the table to initialize
     */
    public void setupTablePanel(List<?> items, String panelTitle, JTable table) {
        initializeTableWithReflection(items, table);
    }
    /**
     * Retrieves the button used to view bills. This button is typically used in the context of viewing client orders.
     * @return the button used to view bills.
     */
    public JButton getViewBillsButton() {
        return viewBillsButton;
    }
    /**
     * Retrieves the frame that contains the client management interface.
     * @return the frame for client management.
     */
    public JFrame getClientFrame() {
        return clientFrame;
    }
    /**
     * Retrieves the frame that contains the product management interface.
     * @return the frame for product management.
     */
    public JFrame getProductFrame() {
        return productFrame;
    }
    /**
     * Retrieves the frame that contains the order management interface.
     * @return the frame for order management.
     */
    public JFrame getOrderFrame() {
        return orderFrame;
    }
    /**
     * Initializes a table by reflecting on the first item in a list to determine the structure of the table columns.
     *
     * @param items a list of items whose fields are used to populate table columns
     * @param table the table to be initialized
     */
    void initializeTableWithReflection(List<?> items, JTable table) {
        if (items == null || items.isEmpty()) {
            table.setModel(new DefaultTableModel());
            return;
        }

        Object firstItem = items.get(0);
        Field[] fields = firstItem.getClass().getDeclaredFields();
        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            columnNames[i] = fields[i].getName();
        }

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Object item : items) {
            Object[] row = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                try {
                    row[i] = fields[i].get(item);
                } catch (IllegalAccessException e) {
                    row[i] = "Access error";
                }
            }
            model.addRow(row);
        }
        table.setModel(model);
    }



    /**
     * Retrieves the table used to display client information.
     * @return the table displaying clients.
     */
    public JTable getClientTable() {
        return clientTable;
    }
    /**
     * Retrieves the table used to display product information.
     * @return the table displaying products.
     */
    public JTable getProductTable() {
        return productTable;
    }
    /**
     * Retrieves the table used to display order information.
     * @return the table displaying orders.
     */
    public JTable getOrderTable() {
        return orderTable;
    }
    /**
     * This method is retained for compatibility reasons and is not used in this updated approach.
     * In previous versions, it was used to initialize combo boxes with client and product data.
     * @param clients a list of clients used to populate client-related UI components.
     * @param products a list of products used to populate product-related UI components.
     */
    public void initializeComboBoxes(List<Client> clients, List<Product> products) {

    }
}
