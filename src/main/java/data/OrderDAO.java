package data;

import model.Order;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Data Access Object specifically for handling database operations related to the Order entity.
 * This class extends the GenericDAO and provides concrete implementations specific to the Order entity.
 */
public class OrderDAO extends GenericDAO<Order> {
    private static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());

    /**
     * Constructor for OrderDAO. It specifies the Order class type to the superclass GenericDAO.
     */
    public OrderDAO() {
        super(Order.class);
    }

    /**
     * Provides the primary key column name for the Order entity.
     * This implementation is necessary because the primary key for Order is not the default 'id'.
     *
     * @return the primary key column name for the Order table
     */
    @Override
    protected String getPrimaryKeyColumnName() {
        return "orderId";
    }

    /**
     * Provides the name of the database table associated with the Order entity.
     * Overrides the default behavior to specify a custom table name, particularly handling the SQL keyword conflict.
     *
     * @return the name of the database table for Order
     */
    @Override
    protected String getTableName() {
        return "order_table";  // Using backticks to handle SQL keyword conflict
    }

    /**
     * Provides a mapping from Order object fields to database column names.
     * This mapping is essential for the generic DAO to perform database operations that require column names.
     *
     * @return a map of Order field names to database column names
     */
    @Override
    protected Map<String, String> getFieldColumnMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("orderId", "orderId");
        mapping.put("clientId", "clientId");
        mapping.put("productId", "productId");
        mapping.put("date", "date");
        mapping.put("quantity", "quantity");
        return mapping;
    }
}
