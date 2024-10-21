package data;

import model.Product;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Data Access Object specifically for handling database operations related to the Product entity.
 * This class extends the GenericDAO and provides concrete implementations specific to the Product entity.
 */
public class ProductDAO extends GenericDAO<Product> {
    private static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());

    /**
     * Constructor for ProductDAO. It specifies the Product class type to the superclass GenericDAO.
     */
    public ProductDAO() {
        super(Product.class);
    }

    /**
     * Provides the name of the database table associated with the Product entity.
     * Overrides the default behavior to specify a custom table name, which is `product` in this case.
     *
     * @return the name of the database table for Product
     */
    @Override
    protected String getTableName() {
        return "`product`";  // Using backticks to handle SQL reserved keywords
    }

    /**
     * Provides the primary key column name for the Product entity.
     * This implementation is necessary because the primary key for Product is not the default 'id'.
     *
     * @return the primary key column name for the Product table
     */
    @Override
    protected String getPrimaryKeyColumnName() {
        return "productId";
    }

    /**
     * Provides a mapping from Product object fields to database column names.
     * This mapping is essential for the generic DAO to perform database operations that require column names.
     * Maps fields like product name, price, and quantity to their respective columns.
     *
     * @return a map of Product field names to database column names
     */
    @Override
    protected Map<String, String> getFieldColumnMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("productId", "productId");
        mapping.put("productName", "productName");
        mapping.put("price", "price");
        mapping.put("quantity", "quantity");
        return mapping;
    }
}
