package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import daoPhysical.*;
import program.Program;;

class TestFrontend {

	@Test
	void testGetAccounts() {
		String correctResult = "[Account[account_id=1, client_id=1, balance=15000.0, is_active=true, created_at=2025-10-26], Account[account_id=2, client_id=1, balance=5000.0, is_active=true, created_at=2025-10-26]]";
		
		assertEquals(correctResult, Program.getAccounts(1));
	}
	
	@Test
	void testGetAccountsFromMissing() {
		String message = "Client missing or client does not have accounts";
		assertEquals(Program.getAccounts(10), message);
	}
	
	@Test
	void convertToCreectSQLDate() {
		
		Date result = Program.convertToSqlDate("2025-07-30");
		
		assertEquals(result.getDate(), 30);
		assertEquals(result.getMonth(), 7-1);
		assertEquals(result.getYear(), 2025-1900);
	}
	
	@Test void getPayments() {
		Date from = Program.convertToSqlDate("2025-10-01");
		Date to = Program.convertToSqlDate("2025-11-01");
		
		assertEquals("3500.0", Program.getPayments(from, to, 1));
	}
	
	@Test void getNoPayments() {
		Date from = Program.convertToSqlDate("2025-10-01");
		Date to = Program.convertToSqlDate("2025-11-01");
		String message = "Client missing or client does not have payments for this range";
		
		assertEquals(message, Program.getPayments(from, to, 10));
	}
	
	@Test void getSum() {
		assertEquals("15000.0", Program.getSum(1));
	}
	
	/*@Disabled
	void makePayment() {
		Credit_card cc = new Credit_card(1, 1, "4111111111111111", Program.convertToSqlDate("2025-12-31"), true);
		
		assertEquals(Program.makePayment(cc, 2, 100), "success");
	}*/

}
