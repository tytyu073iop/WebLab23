package program;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import dao.DAOException;
import daoPhysical.*;

public class Program {
	private static Scanner scanner = new Scanner(System.in);
	
	public static String getAccounts(int client_id) {
		DaoAccounts da = new DaoAccounts();
		
		try {
			String result = da.getClientAccounts(client_id).toString();
			if (result == "[]") {
				return "Client missing or client does not have accounts";
			}
			return result;
		} catch (DAOException e) {
			return e.getLocalizedMessage();
		}
	}
	
	public static Date convertToSqlDate(String userInput) throws IllegalArgumentException {
        if (userInput == null || userInput.trim().isEmpty()) {
            throw new IllegalArgumentException("Date input cannot be empty");
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // Strict parsing
        
        try {
            java.util.Date utilDate = dateFormat.parse(userInput.trim());
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD format: " + userInput);
        }
    }
	
	public static String getPayments(Date from, Date to, int client_id) {
		DaoPayments dp = new DaoPayments();
		
		try {
			String result = dp.getClientPayments(client_id, from, to).toString();
			if (result == "0.0") {
				return "Client missing or client does not have payments for this range";
			}
			return result;
		} catch (DAOException e) {
			return e.getLocalizedMessage();
		}
	}
	
	public static String getSum(int account_id) {
		DaoAccounts da = new DaoAccounts();
		
		try {
			return String.valueOf(da.readAccount(account_id).balance());
		} catch (DAOException e) {
			return e.getLocalizedMessage();
		}
	}
	
	public static String makePayment(Credit_card cc, int to_account_id, double amount) {
		DaoCredit_cards dc = new DaoCredit_cards();
		
		try {
			dc.makePayment(cc, to_account_id, amount);
			return "success";
		} catch (DAOException e) {
			return e.getLocalizedMessage();
		}
	}
	
	public static String deactivate(Credit_card cc) {
		DaoCredit_cards dc = new DaoCredit_cards();
		
		try {
			dc.deactivateCard(cc);
			return "deactivated";
		} catch (DAOException e) {
			return e.getLocalizedMessage();
		}
	}

	 public static void main(String[] args) {
	        System.out.println("=== Banking System ===");
	        
	        while (true) {
	            displayMenu();
	            int choice = getIntInput("Enter your choice (1-6): ");
	            
	            switch (choice) {
	                case 1:
	                    getAccountsMenu();
	                    break;
	                case 2:
	                    getPaymentsMenu();
	                    break;
	                case 3:
	                    getSumMenu();
	                    break;
	                case 4:
	                    makePaymentMenu();
	                    break;
	                case 5:
	                    deactivateCardMenu();
	                    break;
	                case 6:
	                    System.out.println("Thank you for using Banking System. Goodbye!");
	                    return;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	            
	            System.out.println("\n" + "=".repeat(50) + "\n");
	        }
	    }
	    
	    private static void displayMenu() {
	        System.out.println("Please select an option:");
	        System.out.println("1. Get Client Accounts");
	        System.out.println("2. Get Client Payments");
	        System.out.println("3. Get Account Balance");
	        System.out.println("4. Make Payment");
	        System.out.println("5. Deactivate Credit Card");
	        System.out.println("6. Exit");
	    }
	    
	    private static void getAccountsMenu() {
	        System.out.println("\n--- Get Client Accounts ---");
	        int clientId = getIntInput("Enter client ID: ");
	        String result = getAccounts(clientId);
	        System.out.println("Result: " + result);
	    }
	    
	    private static void getPaymentsMenu() {
	        System.out.println("\n--- Get Client Payments ---");
	        int clientId = getIntInput("Enter client ID: ");
	        
	        System.out.println("Enter start date (YYYY-MM-DD): ");
	        String fromDateStr = scanner.nextLine();
	        Date fromDate = null;
	        
	        System.out.println("Enter end date (YYYY-MM-DD): ");
	        String toDateStr = scanner.nextLine();
	        Date toDate = null;
	        
	        try {
	            fromDate = convertToSqlDate(fromDateStr);
	            toDate = convertToSqlDate(toDateStr);
	        } catch (IllegalArgumentException e) {
	            System.out.println("Error: " + e.getMessage());
	            return;
	        }
	        
	        String result = getPayments(fromDate, toDate, clientId);
	        System.out.println("Result: " + result);
	    }
	    
	    private static void getSumMenu() {
	        System.out.println("\n--- Get Account Balance ---");
	        int clientId = getIntInput("Enter client ID: ");
	        DaoAccounts da = new DaoAccounts();
	        List<Account> accounts;
			try {
				accounts = da.getClientAccounts(clientId);
				
				System.out.println("Avaliable choices: ");
		        for (Account account : accounts) {
		            System.out.println("Account ID: " + account.account_id());
		        }
			} catch (DAOException e) {
				System.out.println(e.getLocalizedMessage());
				return;
			}
	        int accountId = getIntInput("Enter account ID: ");
	        String result = getSum(accountId);
	        System.out.println("Account Balance: " + result);
	    }
	    
	    private static void makePaymentMenu() {
	        System.out.println("\n--- Make Payment ---");
	        
	        // Get credit card details
	        int id = getIntInput("Enter credit card id: ");
	        
	        DaoCredit_cards dc = new DaoCredit_cards();
	        Credit_card cc;
			try {
				cc = dc.readCredit_card(id);
				
				int toAccountId = getIntInput("Enter destination account ID: ");
		        double amount = getDoubleInput("Enter payment amount: ");
		        
		        String result = makePayment(cc, toAccountId, amount);
		        System.out.println("Result: " + result);
			} catch (DAOException e) {
				System.out.println(e.getLocalizedMessage());
				return;
			}
	        
	    }
	    
	    private static void deactivateCardMenu() {
	        System.out.println("\n--- Deactivate Credit Card ---");
	        
	        int id = getIntInput("Enter credit card id: ");
	        
	        DaoCredit_cards dc = new DaoCredit_cards();
	        Credit_card cc;
			try {
				cc = dc.readCredit_card(id);
		        
		        String result = deactivate(cc);
		        System.out.println("Result: " + result);
			} catch (DAOException e) {
				System.out.println(e.getLocalizedMessage());
				return;
			}
	    }
	    
	    // Utility methods for input validation
	    private static int getIntInput(String prompt) {
	        while (true) {
	            System.out.print(prompt);
	            try {
	                int value = Integer.parseInt(scanner.nextLine());
	                return value;
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid input. Please enter a valid integer.");
	            }
	        }
	    }
	    
	    private static double getDoubleInput(String prompt) {
	        while (true) {
	            System.out.print(prompt);
	            try {
	                double value = Double.parseDouble(scanner.nextLine());
	                return value;
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid input. Please enter a valid number.");
	            }
	        }
	    }

}
