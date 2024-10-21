package model;

import java.util.Date;

/**
 * Record representing a bill.
 * This record encapsulates details about a bill, including its identifier, the amount, the date the bill was issued, and the associated order ID.
 * It includes a validation check to ensure that the bill amount is not negative.
 *
 * @param billId The unique identifier for the bill.
 * @param amount The amount of the bill. Must be non-negative.
 * @param date The date on which the bill was issued.
 * @param orderId The identifier of the order associated with this bill.
 */
public record Bill(int billId, double amount, Date date, int orderId) {

    /**
     * Constructor for Bill record that includes validation for the bill amount.
     * Throws IllegalArgumentException if the amount is negative, ensuring that all Bill objects have a valid state.
     */
    public Bill {
        if (amount < 0) {
            throw new IllegalArgumentException("Bill amount cannot be negative.");
        }
    }
}
