package daoPhysical;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DAOException;
import dao.Dao;
import dao.JDBCConnectionException;

public class DaoClients extends Dao {	
	public DaoClients() throws JDBCConnectionException {
		super();
	}
	
	private List<Client> parseResult(ResultSet rs) throws SQLException {
		List<Client> clients = new ArrayList<>();
		
		while (rs.next()) {
			Client client = new Client(rs.getInt(1),rs.getString(2),rs.getDate(3));
			
			clients.add(client);
		}
		
		return clients;
	}
	
	public Client readClient(int id) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "SELECT client_id, full_name, created_at FROM clients WHERE client_id= ?";
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
			String sqlRequest = "DELETE FROM clients WHERE client_id= ?";
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
	
	public void createClient(Client client) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "INSERT INTO clients(full_name, created_at) VALUES ?, ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setString(1, client.full_name());
			statement.setDate(2, client.created_at());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}
	
	public void updateClient(Client client) throws DAOException {
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "UPDATE clients set full_name = ?, created_at = ? WHERE client_id = ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setString(1, client.full_name());
			statement.setDate(2, client.created_at());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}

}
