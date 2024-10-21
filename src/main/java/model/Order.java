package model;

import java.util.Date;
/**
 * Represents an order in the system.
 * This class encapsulates details about an order, including its unique identifier, client and product IDs,
 * the date the order was placed, and the quantity of the product ordered.
 */
public class Order {
    private int orderId;
    private int clientId;
    private int productId;
    private Date date;
    private int quantity;

    /**
     * Constructs a new Order with specified details.
     *
     * @param orderId   The unique identifier for the order.
     * @param clientId  The identifier of the client who placed the order.
     * @param productId The identifier of the product ordered.
     * @param date      The date on which the order was placed.
     * @param quantity  The quantity of the product ordered.
     */
    public Order(int orderId,int clientId,int productId, Date date, int quantity) {
  this.orderId=orderId;
        this.productId = productId;
        this.clientId=clientId;
        this.date = date;
        this.quantity = quantity;

    }
    /**
     * Sets the quantity of the product ordered.
     *
     * @param quantity the new quantity of the product.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    /**
     * Gets the quantity of the product ordered.
     *
     * @return the quantity of the product.
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * Gets the order ID.
     *
     * @return the unique identifier of the order.
     */
    public int getOrderId() {
        return orderId;
    }
    /**
     * Gets the date of the order.
     *
     * @return the date on which the order was placed.
     */
    public Date getDate() {
        return date;
    }
    /**
     * Gets the client ID.
     *
     * @return the identifier of the client who placed the order.
     */
    public int getClientId() {
        return clientId;
    }
    /**
     * Gets the product ID.
     *
     * @return the identifier of the product ordered.
     */
    public int getProductId() {
        return productId;
    }
    /**
     * Sets the client ID.
     *
     * @param clientId the new identifier for the client who placed the order.
     */

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    /**
     * Sets the order ID.
     *
     * @param orderId the new unique identifier for the order.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    /**
     * Sets the date of the order.
     *
     * @param date the new date on which the order was placed.
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     * Sets the product ID.
     *
     * @param productId the new identifier for the product ordered.
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Returns a string representation of the order, containing all relevant information.
     *
     * @return a string representation of the order.
     */
    @Override
    public String toString() {
        return "Order [id=" + orderId + " client id= " + clientId  + "product id= " + productId + ", date of order= " + date + ", quantity= " + quantity
                + "]";
    }
}
