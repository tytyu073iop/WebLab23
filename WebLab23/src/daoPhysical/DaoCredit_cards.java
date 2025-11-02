package daoPhysical;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.*;

public class DaoCredit_cards extends Dao {
	public DaoCredit_cards() {
		super();
	}
	
	private List<Credit_card> parseResult(ResultSet rs) throws SQLException {
		List<Credit_card> credit_cards = new ArrayList<>();
		
		while (rs.next()) {
			Credit_card credit_card = new Credit_card(
				    rs.getInt(1),
				    rs.getInt(2),
				    rs.getString(3),
				    rs.getDate(4),
				    rs.getBoolean(5)
				);
			
			credit_cards.add(credit_card);
		}
		
		return credit_cards;
	}
	
	public Credit_card readCredit_card(int id) throws DAOException {
		try {
			Connection connection = cnr.getConnection();
			String sqlRequest = "SELECT card_id, account_id, card_number, expiry_date, is_active FROM credit_cards WHERE card_id= ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			return parseResult(resultSet).getFirst();
		} catch (JDBCConnectionException e) {
			throw new DAOException("Can't obtain user id: " + id, e);
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} finally {
			try {
				cnr.close();
			} catch (JDBCConnectionException e) {
				throw new DAOException("Can't close connection", e);
			}
		}
	}
	
	public void deleteCredit_card(int id) throws DAOException {
		try {
			Connection connection = cnr.getConnection();
			String sqlRequest = "DELETE FROM credit_cards WHERE card_id= ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (JDBCConnectionException e) {
			throw new DAOException("Can't obtain user id: " + id, e);
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} finally {
			try {
				cnr.close();
			} catch (JDBCConnectionException e) {
				throw new DAOException("Can't close connection", e);
			}
		}
	}
	
	public void createCredit_card(Credit_card credit_card) throws DAOException {
		try {
			Connection connection = cnr.getConnection();
			String sqlRequest = "INSERT INTO Credit_cards(account_id, card_number, expiry_date, is_active) VALUES ?, ?, ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, credit_card.account_id());
			statement.setString(2, credit_card.card_number());
			statement.setDate(3, credit_card.expiry_date());
			statement.setBoolean(4, credit_card.is_active());
			statement.executeUpdate();
		} catch (JDBCConnectionException e) {
			throw new DAOException("Can't connect ", e);
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} finally {
			try {
				cnr.close();
			} catch (JDBCConnectionException e) {
				throw new DAOException("Can't close connection", e);
			}
		}
	}
	
	public void updateCredit_card(Credit_card credit_card) throws DAOException {
		try {
			Connection connection = cnr.getConnection();
			String sqlRequest = "UPDATE credit_cards set account_id = ?, card_number = ?, expiry_date = ?, is_active = ? WHERE card_id = ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, credit_card.account_id());
			statement.setString(2, credit_card.card_number());
			statement.setDate(3, credit_card.expiry_date());
			statement.setBoolean(4, credit_card.is_active());
			statement.setInt(5, credit_card.card_id());
			statement.executeUpdate();
		} catch (JDBCConnectionException e) {
			throw new DAOException("Can't connect ", e);
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} finally {
			try {
				cnr.close();
			} catch (JDBCConnectionException e) {
				throw new DAOException("Can't close connection", e);
			}
		}
	}
	
	public void makePayment(Credit_card credit_card, int to_account_id, double amount) throws DAOException {
		DaoPayments dp = new DaoPayments();
		Payment p = new Payment(
				0,
			    credit_card.account_id(),
			    to_account_id,
			    amount,
			    new Date(System.currentTimeMillis()),
			    "SUCCESS"
			);
		
		dp.createPayment(p);
	}
	
	public void deactivateCard(Credit_card credit_card) throws DAOException {
		credit_card = new Credit_card(
			    credit_card.card_id(),
			    credit_card.account_id(),
			    credit_card.card_number(),
			    credit_card.expiry_date(),
			    false  // updated is_active value
			);
		
		updateCredit_card(credit_card);
	}


}
