package validator;

import model.Order;
import business.OrderBL;
/**
 * The {@code OrderIdValidator} class is responsible for validating whether an order ID is unique
 * within the system before a new order is added to avoid conflicts.
 */
public class OrderIdValidator {
    private OrderBL orderBL;
    /**
     * Constructs an {@code OrderIdValidator} with a reference to the business logic layer
     * that handles order operations, specifically for retrieving order details.
     *
     * @param orderBL the business logic layer that provides operations for order data management
     */
    public OrderIdValidator(OrderBL orderBL) {
        this.orderBL = orderBL;
    }
    /**
     * Validates that an order ID is not already used in the system. If the ID is found,
     * an {@code IllegalArgumentException} is thrown to prevent duplication.
     *
     * @param orderId the ID of the order to validate for uniqueness
     * @throws IllegalArgumentException if the order ID is already in use, indicating duplication
     */
    public void validate(int orderId) {
        if (orderBL.findById(orderId) != null) {
            throw new IllegalArgumentException("Order ID already in use.");
        }
    }
}
