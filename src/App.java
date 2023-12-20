import java.sql.Connection;
import java.sql.Date;
import java.util.Scanner;
import config.DB;
import dao.AccountDao;
import dao.CustomerDao;
import model.Account;
import model.Customer;

/**
 * =====================================================================================================
 * ================================= Created By : Umesh Kumar ==========================================
 * ================================= Created On : 19-12-2023  ==========================================
 * =====================================================================================================
 * | Status : Open
 */
public class App {
    public static void main(String[] args) throws Exception {
        int choice;
        Scanner sc = new Scanner(System.in);
        try (Connection conn = DB.getConnection()) {
            do {
                System.out.println(
                        "\n<============================  W E L C O M E     T O     A A P N A     B A N K  ============================>\n");
                System.out.println("1. Add new Bank Account \t\t2. Show Customers List\t\t3. Search By Account Number "+
                "\n4. Deposit Money \t\t\t5. Credit Money \t\t6. Check Balance\n0. Exist ");
                System.out.print("\nEnter Your Choice : ");
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        // Customer Information
                        System.out.println("\n<---------------- Customer Information ---------------->");
                        sc.nextLine();
                        System.out.print("\nEnter Full Name: ");
                        String fullname = sc.nextLine();
                        System.out.print("Enter Your Email Id : ");
                        String email = sc.nextLine();
                        System.out.print("Enter Your Phone Number : ");
                        Long phone = sc.nextLong();
                        sc.nextLine();
                        System.out.print("Enter Your Address : ");
                        String address = sc.nextLine();

                        // Account Information
                        System.out.println("\n<---------------- Account Information ---------------->");
                        System.out.print("\nEnter Your Account Type (Saving / Checking) : ");
                        String accountType = sc.nextLine();
                        System.out.print("Enter Balance : ");
                        long balance = sc.nextLong();

                        Customer customer = new Customer();
                        customer.setFullname(fullname);
                        customer.setEmail(email);
                        customer.setPhone(phone);
                        customer.setAddress(address);

                        int customerId = CustomerDao.addCustomer(conn, customer); // add the customer

                        Account account = new Account();
                        account.setCustomerId(customerId);
                        account.setAccountType(accountType);
                        account.setBalance(balance);
                        long timestamp = System.currentTimeMillis();
                        account.setOpenDate(new Date(timestamp));
                        
                        AccountDao.addAccount(account, conn);  // add account 

                        break;
                    case 2:
                        System.out.println("\n<-------------- List of customers -------------->\n");
                        CustomerDao.listOfCustomers(conn);
                        break;

                    case 3:
                        System.out.println("\n<-------------- Search Customer Details By Account Number -------------->\n");
                        System.out.print("Enter Your Account Number : ");
                        long accountNumber = sc.nextLong();
                        sc.nextLine();
                        CustomerDao.searchByAccountNumber(conn,accountNumber);
                        break;
                    case 4:
                        System.out.println("\n<-------------- Deposit Money -------------->\n");
                        System.out.print("Enter Your Account Number : ");
                        accountNumber = sc.nextLong();
                        sc.nextLine();
                        System.out.println(accountNumber);
                        break; 
                    case 0:
                        System.out.println("\n<-------------------- Thanks for visiting -------------------->\n");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("\nInvalid Choice ! Try Again ....\n");
                        break;
                }

            } while (choice != 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        sc.close();
        System.out.println();

    }
}
