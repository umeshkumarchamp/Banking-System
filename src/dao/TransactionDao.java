package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import model.Transaction;

public class TransactionDao {

    public static Scanner sc = new Scanner(System.in);

    public static void depositMoney(Connection conn, long accountNumber) throws SQLException {
        conn.setAutoCommit(false);
        // Insert account details 
        String query = "SELECT * FROM accounts WHERE account_number = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setLong(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                int accountId = rs.getInt("id");
                int balance = rs.getInt("balance");

                System.out.print("\nEnter Amount : ");
                int amount = sc.nextInt();
                long timestamp = System.currentTimeMillis();

                Transaction tranObj = new Transaction();  // Creating Transaction object 
                tranObj.setAccountId(accountId);
                tranObj.setAmount(amount);
                tranObj.setTransactionType("Deposit"); 
                tranObj.setTransactionDate(new Date(timestamp));
                
                String tran = "INSERT INTO transactions (amount, transaction_type, transaction_date, account_id) VALUES (?,?,?,?)";
                PreparedStatement tr = conn.prepareStatement(tran);  
                tr.setInt(1, tranObj.getAmount());
                tr.setString(2, tranObj.getTransactionType());
                tr.setDate(3, tranObj.getTransactionDate());
                tr.setInt(4, tranObj.getAccountId());

                int res1 = tr.executeUpdate();

                int newBalance = balance + amount; 
                String updateBalance = "UPDATE accounts SET balance = ? WHERE id = ?";
                PreparedStatement updateAmt = conn.prepareStatement(updateBalance); 
                updateAmt.setInt(1, newBalance);
                updateAmt.setInt(2, accountId);
                int res2 = updateAmt.executeUpdate();
                if(res1 > 0 && res2 > 0){
                    System.out.println("\nTransaction Successful.");
                    System.out.println("Total Amount : " + newBalance);
                }else{
                    System.out.println("Transaction Failed !");
                }

            }else{
                System.out.println("\nAccount Number Does Not Exist !");
            }

            
        }catch(Exception e){
            e.printStackTrace();
            conn.rollback();
        }finally {
            // Always set auto-commit back to true after completing the transaction
            conn.setAutoCommit(true);
        }
        // System.out.println("Something went wrong");
        // return -1; // Return -1 to indicate an error

    }


    
}
