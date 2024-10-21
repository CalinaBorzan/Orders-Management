package business;

import data.ClientDAO;
import model.Client;
import validator.EmailValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Business logic class for managing client operations.
 * This class provides methods to add, edit, delete, and find clients using a data access object (DAO).
 */
public class ClientBL {
    private ClientDAO clientDAO;
    private static final Logger LOGGER = Logger.getLogger(ClientBL.class.getName());
    private EmailValidator emailValidator = new EmailValidator();

    /**
     * Constructs a ClientBL object with a specified ClientDAO.
     *
     * @param clientDAO the data access object used for client operations
     */
    public ClientBL(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    /**
     * Adds a client to the database after validation.
     *
     * @param client the Client object to be added
     * @return the added Client object
     */
    public Client addClient(Client client) {
        validateClient(client);
        return clientDAO.insert(client);
    }

    /**
     * Deletes a client from the database.
     *
     * @param clientId the ID of the client to delete
     * @return true if the client was successfully deleted, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean deleteClient(int clientId) throws SQLException {
        return clientDAO.delete(clientId);
    }

    /**
     * Edits a client's details in the database after validation.
     *
     * @param client the Client object to be updated
     * @return the updated Client object
     */
    public Client editClient(Client client) {
        validateClient(client);
        return clientDAO.update(client);
    }

    /**
     * Finds a client by their ID.
     *
     * @param clientId the ID of the client to find
     * @return the found Client object, or null if no client is found with the provided ID
     */
    public Client findById(int clientId) {
        return clientDAO.findById(clientId);
    }

    /**
     * Retrieves all clients from the database.
     *
     * @return a list of all Client objects
     */
    public List<Client> listAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Validates the necessary fields of a Client object.
     *
     * @param client the Client object to validate
     * @throws IllegalArgumentException if first name or last name is null
     */
    private void validateClient(Client client) {
        if (client.getFirstName() == null || client.getLastName() == null) {
            throw new IllegalArgumentException("Client first name and last name cannot be null");
        }

        emailValidator.validate(client.getEmail());
    }
}
