package business;

import data.ProductDAO;
import model.Product;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Business logic class for managing product operations.
 * This class handles CRUD operations for products using a data access object (DAO).
 */
public class ProductBL {
    private static final Logger LOGGER = Logger.getLogger(ProductBL.class.getName());
    private ProductDAO productDAO;

    /**
     * Constructs a ProductBL object with a specified ProductDAO.
     *
     * @param productDAO the data access object used for product operations
     */
    public ProductBL(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * Adds a product to the database after validation.
     *
     * @param product the Product object to be added
     * @return the added Product object
     */
    public Product addProduct(Product product) {
        validateProduct(product);
        return productDAO.insert(product);
    }

    /**
     * Deletes a product from the database.
     *
     * @param productId the ID of the product to delete
     * @return true if the product was successfully deleted, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean deleteProduct(int productId) throws SQLException {
        return productDAO.delete(productId);
    }

    /**
     * Edits a product's details in the database after validation.
     *
     * @param product the Product object to be updated
     * @return the updated Product object
     */
    public Product editProduct(Product product) {
        validateProduct(product);
        return productDAO.update(product);
    }

    /**
     * Finds a product by its ID.
     *
     * @param productId the ID of the product to find
     * @return the found Product object, or null if no product is found with the provided ID
     */
    public Product findById(int productId) {
        return productDAO.findById(productId);
    }

    /**
     * Retrieves all products from the database.
     *
     * @return a list of all Product objects
     */
    public List<Product> listAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Validates the necessary fields of a Product object.
     *
     * @param product the Product object to validate
     * @throws IllegalArgumentException if the product name is null or empty, or if quantity or price are negative
     */
    private void validateProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (product.getPrice() < 0.0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}
