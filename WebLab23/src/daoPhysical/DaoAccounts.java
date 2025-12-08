package daoPhysical;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.*;

public class DaoAccounts extends Dao {
	
	public DaoAccounts() throws JDBCConnectionException {
		super();
	}
	
	private List<Account> parseResult(ResultSet rs) throws SQLException {
		List<Account> accounts = new ArrayList<>();
		
		while (rs.next()) {
			Account account = new Account(
				    rs.getInt(1),
				    rs.getInt(2),
				    rs.getDouble(3),
				    rs.getBoolean(4),
				    rs.getDate(5)
				);
			
			accounts.add(account);
		}
		
		return accounts;
	}
	
	public Account readAccount(int id) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "SELECT account_id, client_id, balance, is_active, created_at FROM accounts WHERE account_id= ?";
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
	
	public void deleteAccount(int id) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "DELETE FROM accounts WHERE account_id= ?";
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
	
	public void createAccount(Account account) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "INSERT INTO accounts(client_id, balance, is_active, created_at) VALUES ?, ?, ?, ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, account.client_id());
			statement.setDouble(2, account.balance());
			statement.setBoolean(3, account.is_active());
			statement.setDate(4, account.created_at());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}
	
	public void updateAccount(Account account) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "UPDATE accounts set client_id = ?, balance = ?, is_active = ?, created_at = ? WHERE account_id = ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, account.client_id());
			statement.setDouble(2, account.balance());
			statement.setBoolean(3, account.is_active());
			statement.setDate(4, account.created_at());
			statement.setInt(5, account.account_id());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}
	
	public List<Account> getClientAccounts(int client_id) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "SELECT account_id, client_id, balance, is_active, created_at FROM accounts WHERE client_id= ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, client_id);
			ResultSet resultSet = statement.executeQuery();
			return parseResult(resultSet);
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}
}
