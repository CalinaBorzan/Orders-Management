package data;

import connection.ConnectionFactory;
import model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for handling database operations related to bills.
 * Provides methods to insert and retrieve bill records.
 */
public class BillDAO {

    /**
     * Inserts a bill into the database.
     * This method will add a new bill record to the database with the specified details from the Bill object.
     *
     * @param bill the Bill object containing the details to be recorded
     * @return Bill object with the generated key (bill ID) from the database
     * @throws SQLException if an SQL error occurs during the insertion process or no rows are affected
     */
    public Bill insertBill(Bill bill) throws SQLException {
        String sql = "INSERT INTO log (price, date, orderId) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, bill.amount());
            stmt.setTimestamp(2, new Timestamp(bill.date().getTime()));
            stmt.setInt(3, bill.orderId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating bill failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Bill(
                            generatedKeys.getInt(1), // The auto-generated ID
                            bill.amount(),
                            bill.date(),
                            bill.orderId()
                    );
                } else {
                    throw new SQLException("Creating bill failed, no ID obtained.");
                }
            }
        }
    }

    /**
     * Retrieves all bills from the database.
     * This method fetches all bill records from the database and returns them as a list of Bill objects.
     *
     * @return a list of Bill objects, each representing a bill record in the database
     * @throws SQLException if an SQL error occurs during the retrieval process
     */
    public List<Bill> getAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM log";
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Bill bill = new Bill(
                        rs.getInt("billId"),
                        rs.getDouble("price"),
                        rs.getTimestamp("date"),
                        rs.getInt("orderId")
                );
                bills.add(bill);
            }
        }
        return bills;
    }
}
