package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Customer;

/**
 * Custoemr Dao Class
 */
public class CustomerDao {

    /**
     * Add a new customer
     * 
     * @param conn
     * @param c
     * @return
     * @throws SQLException
     */
    public static int addCustomer(Connection conn, Customer c) throws SQLException {
        conn.setAutoCommit(false);
        String query = "INSERT INTO customer (fullname, email, phone, address) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, c.getFullname());
            pstmt.setString(2, c.getEmail());
            pstmt.setLong(3, c.getPhone());
            pstmt.setString(4, c.getAddress());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Commit the transaction
                conn.commit();
                // Retrieve the generated keys
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int customerId = generatedKeys.getInt(1);
                        // System.out.println("Customer Added Successfully with ID: " + customerId);
                        return customerId;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the exception for debugging
        } finally {
            // Always set auto-commit back to true after completing the transaction
            conn.setAutoCommit(true);
        }
        System.out.println("Something went wrong");
        return -1; // Return -1 to indicate an error

    }

    /**
     * Get List of customers
     * 
     * @param conn
     * @throws SQLException
     */
    public static void listOfCustomers(Connection conn) throws SQLException {

        String query = "SELECT * FROM customer ORDER BY id";
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            do{
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFullname(rs.getString("fullname"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getLong("phone"));
                customer.setAddress(rs.getString("address"));
                System.out.println(customer.toString());
            }while (rs.next());
        }else{
            System.out.println("No such data found!!!");
        }
    }

    /**
     * Searching Customer Details By Account Number
     * @param conn
     * @param accountNumber
     */
    public static void searchByAccountNumber(Connection conn, long accountNumber) {
        // Join Query for Customer and Account
        String query = "SELECT a.id AS account_id, a.account_number as account_number, a.account_type as account_type, a.balance as balance,"
        +"a.open_date as date, c.id as customer_id, c.fullname as fullname, c.email as email, c.phone as phone, c.address as address"
        +" FROM accounts AS a JOIN customer AS c ON c.id = a.customer_id WHERE account_number = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setLong(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                int customerId = rs.getInt("customer_id");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                long accountNo = rs.getLong("account_number");
                String accountType = rs.getString("account_type");
                int balance = rs.getInt("balance");
                Date date = rs.getDate("date");
                System.out.println();
                System.out.println("CustomerId \t: " + customerId);
                System.out.println("Fullname \t: " + fullname);
                System.out.println("Email-Id \t: " + email);
                System.out.println("Phone \t\t: " + phone);
                System.out.println("Address \t: " + address);
                System.out.println("AccountType \t: " + accountType);
                System.out.println("AccountNumber \t: " + accountNo);
                System.out.println("Balance \t: " + balance);
                System.out.println("Created Date \t: " + date);

            }else{
                System.out.println("\nAccount Not Found ! ");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
