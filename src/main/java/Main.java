import business.ClientBL;
import business.OrderBL;
import business.ProductBL;
import data.BillDAO;
import data.ClientDAO;
import data.OrderDAO;
import data.ProductDAO;
import presentation.Controller;
import presentation.View;
/**
 * The {@code Main} class serves as the entry point for the application. It initializes
 * the necessary components for the business logic and presentation layers and links them
 * together to form a functioning system for managing clients, products, and orders.
 */
public class Main {

    /**
     * The main method sets up the entire application by creating instances of data access objects,
     * business logic handlers, and the presentation view, and then starts the application by
     * initializing the controller with these components.
     *
     * @param args the command line arguments, not used in this application
     */
    public static void main(String[] args) {

        ClientDAO clientDAO = new ClientDAO();
        ProductDAO productDAO = new ProductDAO();
        OrderDAO orderDAO = new OrderDAO();
        BillDAO billDAO = new BillDAO();

        ClientBL clientBL = new ClientBL(clientDAO);
        ProductBL productBL = new ProductBL(productDAO);
        OrderBL orderBL = new OrderBL(orderDAO, productDAO, clientDAO, billDAO);

        View view = new View();
        Controller controller = new Controller(view, clientBL, productBL, orderBL);
    }
}
