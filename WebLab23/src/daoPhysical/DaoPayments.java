package daoPhysical;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.DAOException;
import dao.Dao;
import dao.JDBCConnectionException;

public class DaoPayments extends Dao {

	
	public DaoPayments() throws JDBCConnectionException {
		super();
	}
	
	private List<Payment> parseResult(ResultSet rs) throws SQLException {
		List<Payment> payments = new ArrayList<>();
		
		while (rs.next()) {
			Payment payment = new Payment(
				    rs.getInt(1),
				    rs.getInt(2),
				    rs.getInt(3),
				    rs.getDouble(4),
				    rs.getDate(5),
				    rs.getString(6)
				);
			
			payments.add(payment);
		}
		
		return payments;
	}
	
	public Payment readPayment(int id) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "SELECT payment_id, from_account_id, to_account_id, amount, payment_date, status FROM Payments WHERE Payment_id= ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			return parseResult(resultSet).getFirst();
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}
	
	public void deletePayment(int id) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "DELETE FROM payments WHERE Payment_id= ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}
	
	public void createPayment(Payment payment) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "INSERT INTO Payments(from_account_id, to_account_id, amount, payment_date, status) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, payment.from_account_id());
			statement.setInt(2, payment.to_account_id());
			statement.setDouble(3, payment.amount());
			statement.setDate(4, payment.payment_date());
			statement.setString(5, payment.status());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}
	
	public void updatePayment(Payment payment) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "UPDATE payments set from_account_id = ?, to_account_id = ?, amount = ?, payment_date = ?, status = ? WHERE Payment_id = ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, payment.from_account_id());
			statement.setInt(2, payment.to_account_id());
			statement.setDouble(3, payment.amount());
			statement.setDate(4, payment.payment_date());
			statement.setString(5, payment.status());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}
	
	public Double getClientPayments(int client_id, Date from, Date to) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "SELECT sum(amount) FROM payments WHERE from_account_id IN (SELECT account_id FROM accounts WHERE client_id= ?) AND payment_date BETWEEN ? AND ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, client_id);
			statement.setDate(2, from);
			statement.setDate(3, to);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getDouble(1);
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}

}
