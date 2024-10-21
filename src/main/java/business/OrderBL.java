package business;

import connection.ConnectionFactory;
import data.BillDAO;
import data.ClientDAO;
import data.OrderDAO;
import data.ProductDAO;
import model.Bill;
import model.Client;
import model.Order;
import model.Product;
import validator.ClientValidator;
import validator.OrderIdValidator;
import validator.ProductValidator;
import validator.QuantityValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Business logic class for managing orders.
 * This class handles operations related to creating orders, managing products,
 * clients, and bills through various data access objects.
 */
public class OrderBL {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private ClientDAO clientDAO;
    private BillDAO billDAO;

    private ClientValidator clientValidator;
    private ProductValidator productValidator;
    private QuantityValidator quantityValidator;
    private OrderIdValidator orderIdValidator;

    /**
     * Constructs an OrderBL object with specified DAOs for handling order, product,
     * client, and bill data.
     *
     * @param orderDAO the data access object for order operations
     * @param productDAO the data access object for product operations
     * @param clientDAO the data access object for client operations
     * @param billDAO the data access object for billing operations
     */
    public OrderBL(OrderDAO orderDAO, ProductDAO productDAO, ClientDAO clientDAO, BillDAO billDAO) {
        this.clientDAO = clientDAO;
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
        this.billDAO = billDAO;

        // Initialize validators
        this.clientValidator = new ClientValidator(new ClientBL(clientDAO));
        this.productValidator = new ProductValidator(new ProductBL(productDAO));
        this.quantityValidator = new QuantityValidator();
        this.orderIdValidator = new OrderIdValidator(this);
    }

    /**
     * Creates an order with the specified details, validates client, product, and quantity,
     * and commits a transaction that includes updating product inventory, creating an order record,
     * and generating a bill.
     *
     * @param orderId the ID of the order
     * @param client the client making the order
     * @param product the product being ordered
     * @param quantity the quantity of the product ordered
     * @return true if the order creation and transaction are successful, false otherwise
     * @throws SQLException if a database access error occurs, or this method is unable to execute SQL statements
     */
    public boolean createOrder(int orderId, Client client, Product product, int quantity) throws SQLException {
        clientValidator.validate(client);
        productValidator.validate(product, quantity);
        quantityValidator.validate(quantity);
        orderIdValidator.validate(orderId);

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);

            product.setQuantity(product.getQuantity() - quantity);
            productDAO.update(product);

            Order order = new Order(orderId, client.getId(), product.getId(), new Date(), quantity);
            orderDAO.insert(order);

            double billAmount = quantity * product.getPrice();
            Bill bill = new Bill(0, billAmount, new Date(), order.getOrderId());
            billDAO.insertBill(bill);

            conn.commit();
            return true;
        } catch (Exception e) {
            throw new SQLException("Error creating order: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves all bills from the database.
     *
     * @return a list of all Bill objects
     * @throws SQLException if a database access error occurs
     */
    public List<Bill> getAllBills() throws SQLException {
        return billDAO.getAllBills();
    }

    /**
     * Retrieves a list of all orders from the database.
     *
     * @return a list of all Order objects
     */
    public List<Order> listAllOrders() {
        return orderDAO.findAll();
    }

    /**
     * Finds an order by its ID.
     *
     * @param orderId the ID of the order to find
     * @return the Order object if found, null otherwise
     */
    public Order findById(int orderId) {
        return orderDAO.findById(orderId);
    }
}
