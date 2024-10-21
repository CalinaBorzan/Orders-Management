package validator;

import model.Client;
import business.ClientBL;
/**
 * The {@code ClientValidator} class is responsible for validating client operations,
 * ensuring that a client exists before an operation is performed.
 */
public class ClientValidator {
    private ClientBL clientBL;
    /**
     * Constructs a {@code ClientValidator} with a reference to a business logic handler.
     *
     * @param clientBL the business logic layer that provides operations for client data
     */
    public ClientValidator(ClientBL clientBL) {
        this.clientBL = clientBL;
    }
    /**
     * Validates the existence of a client in the persistence layer.
     * If the client does not exist, an exception is thrown.
     *
     * @param client the client to validate
     * @throws IllegalArgumentException if the client is null or does not exist in the database
     */
    public void validate(Client client) {
        if (client == null || clientBL.findById(client.getId()) == null) {
            throw new IllegalArgumentException("Client does not exist.");
        }
    }
}
