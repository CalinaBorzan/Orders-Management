package validator;

import model.Product;
import business.ProductBL;
/**
 * The {@code ProductValidator} class is responsible for validating product-related operations,
 * ensuring that the product exists in the system and that there is sufficient quantity available
 * for transactions.
 */
public class ProductValidator {
    private ProductBL productBL;
    /**
     * Constructs a {@code ProductValidator} with a reference to the business logic layer
     * that handles product operations. This validator uses the business layer to fetch
     * product details for validation purposes.
     *
     * @param productBL the business logic layer responsible for managing product data
     */
    public ProductValidator(ProductBL productBL) {
        this.productBL = productBL;
    }

    /**
     * Validates the existence of the product and checks if the available quantity meets
     * the required quantity for a transaction. If the product does not exist or if there
     * is insufficient stock, an exception is thrown.
     *
     * @param product the product to validate
     * @param quantity the required quantity for validation against available stock
     * @throws IllegalArgumentException if the product does not exist or if there is insufficient quantity available
     */
    public void validate(Product product, int quantity) {
        if (product == null || productBL.findById(product.getId()) == null) {
            throw new IllegalArgumentException("Product does not exist.");
        }
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient product quantity.");
        }
    }
}
