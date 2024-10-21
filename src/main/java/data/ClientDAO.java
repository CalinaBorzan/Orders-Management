package data;

import model.Client;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Data Access Object specifically for handling database operations related to the Client entity.
 * Extends the GenericDAO class tailored to the Client model, handling CRUD operations and more.
 */
public class ClientDAO extends GenericDAO<Client> {
    private static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

    /**
     * Constructs a ClientDAO for handling Client entities, initializing with the Client class type.
     */
    public ClientDAO() {
        super(Client.class);
    }

    /**
     * Provides the name of the database table associated with the Client entity.
     * This method is used to specify the table name for SQL queries constructed by generic methods in the superclass.
     *
     * @return the name of the database table for clients
     */
    @Override
    protected String getTableName() {
        return "`client`";  // Use backticks for SQL table names to handle potential reserved keywords.
    }

    /**
     * Provides a mapping from Client object fields to database column names.
     * This mapping is essential for the generic DAO to perform database operations that require column names.
     *
     * @return a map of Client field names to database column names
     */
    @Override
    protected Map<String, String> getFieldColumnMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("id", "id");
        mapping.put("lastName", "last_name");
        mapping.put("firstName", "first_name");
        mapping.put("email", "email");
        mapping.put("age", "age");
        mapping.put("address", "address");
        return mapping;
    }

}
