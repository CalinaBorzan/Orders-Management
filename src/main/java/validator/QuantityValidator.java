package validator;
/**
 * The {@code QuantityValidator} class is responsible for ensuring that quantities
 * used in business operations are valid, specifically that they are greater than zero.
 * This validation is critical for operations like placing orders, adjusting inventory, etc.,
 * where a non-positive quantity would not make sense and could lead to errors or incorrect business logic execution.
 */
public class QuantityValidator {
    /**
     * Validates that a given quantity is greater than zero. This method is typically used
     * before performing operations that modify or rely on item counts, ensuring the operation
     * makes logical sense in a business context (e.g., selling, restocking).
     *
     * @param quantity the quantity to validate
     * @throws IllegalArgumentException if the quantity is less than or equal to zero
     */
    public void validate(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }
}
