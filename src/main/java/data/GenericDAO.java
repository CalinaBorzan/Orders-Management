package data;

import java.lang.reflect.*;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import connection.ConnectionFactory;


/**
 * Abstract generic Data Access Object (DAO) class that provides the common functionality for all DAO classes.
 * This class handles basic CRUD operations with database for any type of entity.
 *
 * @param <T> the type of the entity that this DAO will manage
 */
public abstract class GenericDAO<T> {
    private static final Logger LOGGER = Logger.getLogger(GenericDAO.class.getName());
    private final Class<T> type;
    /**
     * Constructor for the GenericDAO.
     *
     * @param type the class of the entity this DAO handles.
     */
    public GenericDAO(Class<T> type) {
        this.type = type;
    }
    /**
     * Returns the table name based on the entity type.
     *
     * @return the simple name of the class, which is used as the table name.
     */
    protected String getTableName() {

        return type.getSimpleName();
    }
    /**
     * Constructs a query for selecting entities based on a specific field value.
     *
     * @param field the field name used in the WHERE clause of the SQL query
     * @return the SQL query string for selecting entities
     */
    private String selectFromQuery(String field) {
        return "SELECT * FROM " + getTableName() + " WHERE " + field + " = ?";
    }
    /**
     * Gets the primary key column name of the table associated with the entity.
     * This method should be overridden if the primary key column is not named 'id'.
     *
     * @return the primary key column name
     */
    protected String getPrimaryKeyColumnName() {
        return "id";
    }

    /**
     * Retrieves all entities of type T from the database.
     * This method executes a SELECT query and returns a list of all entities found in the corresponding table.
     *
     * @return a list of all entities of type T found in the database, or an empty list if no entities are found.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            list = createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return list;
    }

    /**
     * Inserts a new entity into the database.
     * This method dynamically constructs an SQL INSERT statement to add a new record to the database using the entity's fields.
     *
     * @param t the entity to insert into the database
     * @return the entity with its ID updated to reflect the generated key from the database, or null if the insertion fails
     * @throws SQLException if there is an issue executing the insert command
     */
    public T insert(T t) {
        Map<String, String> fieldColumnMapping = getFieldColumnMapping();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        List<Field> insertableFields = new ArrayList<>();


        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.getName().equals(getPrimaryKeyColumnName())) {
                insertableFields.add(field);
            }
        }


        String query = "INSERT INTO " + getTableName() + " (" +
                insertableFields.stream()
                        .map(f -> fieldColumnMapping.get(f.getName()))
                        .collect(Collectors.joining(", ")) +
                ") VALUES (" +
                insertableFields.stream()
                        .map(f -> "?")
                        .collect(Collectors.joining(", ")) +
                ")";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            for (Field field : insertableFields) {
                statement.setObject(index++, field.get(t));
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating entity failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Field idField = type.getDeclaredField(getPrimaryKeyColumnName());
                idField.setAccessible(true);
                Object idValue = generatedKeys.getObject(1);
                if (idField.getType().equals(int.class) && idValue instanceof BigInteger) {
                    idField.set(t, ((BigInteger) idValue).intValue());
                } else {
                    idField.set(t, idValue);
                }
            } else {
                throw new SQLException("Creating entity failed, no ID obtained.");
            }
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
            return null;
        } finally {
            ConnectionFactory.close(generatedKeys);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Finds a single entity by its ID.
     *
     * @param id the ID of the entity to find
     * @return the found entity, or null if no entity is found
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        T object = null;
        String query =selectFromQuery(getPrimaryKeyColumnName());

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> items = createObjects(resultSet);
            if (!items.isEmpty()) {
                object = items.get(0);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, getTableName() + " DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return object;
    }
    /**
     * Updates an entity in the database.
     * This method constructs an SQL update query dynamically based on the entity's fields.
     *
     * @param t the entity to update
     * @return the updated entity, or null if no update was performed
     */
    public T update(T t) {
        Map<String, String> fieldColumnMapping = getFieldColumnMapping();
        Connection connection = null;
        PreparedStatement statement = null;
        StringBuilder setClause = new StringBuilder();

        try {
            connection = ConnectionFactory.getConnection();
            Field idField = null;
            Object idValue = null;


            boolean first = true;
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().equalsIgnoreCase(getPrimaryKeyColumnName())) {
                    idField = field;
                    idValue = field.get(t);
                    continue;
                }

                if (!first) {
                    setClause.append(", ");
                }
                setClause.append(fieldColumnMapping.get(field.getName())).append(" = ?");
                first = false;
            }
            setClause.append(" WHERE ").append(fieldColumnMapping.get(getPrimaryKeyColumnName())).append(" = ?");

            // SQL query construction
            String query = "UPDATE " + getTableName() + " SET " + setClause.toString();
            statement = connection.prepareStatement(query);

            int index = 1;
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.getName().equalsIgnoreCase(getPrimaryKeyColumnName())) {
                    statement.setObject(index++, field.get(t));
                }
            }
            statement.setObject(index, idValue);


            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No rows updated - entity may not exist or update data identical to existing data.");
            }
            return t;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {  // Check if the SQL state relates to foreign key constraint
                throw new RuntimeException("Cannot update: foreign key constraint violation", e);
            }
            LOGGER.log(Level.SEVERE, "Error updating entity: " + e.getMessage(), e);
            return null;
        } catch (IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "Illegal access during reflection: " + e.getMessage(), e);
            return null;
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
    /**
     * Deletes an entity from the database by its primary key ID.
     * This method executes an SQL DELETE statement to remove a record from the database.
     *
     * @param id the ID of the entity to delete
     * @return true if the entity was successfully deleted, false otherwise
     * @throws SQLException if there is an error during the deletion process
     */
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE " + getPrimaryKeyColumnName() + " = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {

            if (e.getSQLState().startsWith("23")) {
                throw new SQLIntegrityConstraintViolationException(e);
            } else {
                throw e;
            }
        }
    }

    /**
     * Creates a list of entities from a ResultSet.
     * This method utilizes reflection to instantiate objects based on the ResultSet metadata.
     *
     * @param resultSet the ResultSet from which to create objects
     * @return a list of instantiated objects
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            Constructor<?>[] constructors = type.getDeclaredConstructors();

            while (resultSet.next()) {
                for (Constructor<?> constructor : constructors) {
                    if (constructor.getParameterCount() == columnCount) {
                        Object[] params = new Object[columnCount];
                        for (int i = 0; i < columnCount; i++) {
                            params[i] = resultSet.getObject(i + 1);
                        }
                        try {
                            T instance = (T) constructor.newInstance(params);
                            list.add(instance);
                            break;
                        } catch (IllegalArgumentException | InstantiationException | InvocationTargetException e) {
                            LOGGER.log(Level.SEVERE, "Instantiation failed", e);
                        }
                    }
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "Error creating object from ResultSet", e);
        }
        return list;
    }
    /**
     * Provides a mapping from entity fields to database column names.
     * This method must be implemented by subclasses to map entity fields to the corresponding database columns.
     *
     * @return a Map where keys are field names and values are column names
     */
    protected abstract Map<String, String> getFieldColumnMapping();
}
