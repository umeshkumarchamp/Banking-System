package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Account;

public class AccountDao {

    /**
     * Add a new account
     * @param conn
     * @param account
     * @throws SQLException
     */
    public static int addAccount(Account account, Connection conn) throws SQLException{
        conn.setAutoCommit(false);
        long accountNumber = generateUniqueAccountNumber();
        account.setAccountNumber(accountNumber);
        // Insert account details 
        String query = "INSERT INTO accounts (account_number, account_type, balance, open_date, customer_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, account.getAccountNumber());
            pstmt.setString(2, account.getAccountType());
            pstmt.setLong(3, account.getBalance());
            pstmt.setDate(4, account.getOpenDate());
            pstmt.setInt(5, account.getCustomerId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // Commit the transaction
                conn.commit();
                // Retrieve the generated keys
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int accountId = generatedKeys.getInt(1);
                        System.out.println("Successfully added account with ID: " + accountId);
                        return accountId;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
            conn.rollback();
        }finally {
            // Always set auto-commit back to true after completing the transaction
            conn.setAutoCommit(true);
        }
        System.out.println("Something went wrong");
        return -1; // Return -1 to indicate an error
    }




    /**
     * Generate unique account number
     * @return
     */
    private static long generateUniqueAccountNumber() {
        // Get current timestamp in milliseconds
        long timestamp = System.currentTimeMillis();

        // Generate a random number between 10000000000 and 99999999999
        long randomNumber = (int) (Math.random() * 900000L) + 100000L;

        // Combine "99", timestamp, and random number to form a 12-digit integer
        long accountNumber = Integer.parseInt("99" + ((timestamp % 100000L) + randomNumber));

        return accountNumber;
    }


}
