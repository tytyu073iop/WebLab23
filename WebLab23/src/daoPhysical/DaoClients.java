package daoPhysical;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.DAOException;
import dao.Dao;
import dao.JDBCConnectionException;

public class DaoClients extends Dao {	
	private static final Logger LOGGER = LogManager.getLogger();
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
		LOGGER.info("getting client");
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "SELECT client_id, full_name, created_at FROM clients WHERE client_id= ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			return parseResult(resultSet).getFirst();
		} catch (SQLException e) {
			LOGGER.error("Can't create statement", e);
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}
	
	public void deletePayment(int id) throws DAOException {
		LOGGER.info("deleting client");
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "DELETE FROM clients WHERE client_id= ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Can't create statement", e);
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}
	
	public void createClient(Client client) throws DAOException {
		LOGGER.info("creating client");
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "INSERT INTO clients(full_name, created_at) VALUES ?, ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setString(1, client.full_name());
			statement.setDate(2, client.created_at());
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Can't create statement", e);
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}
	
	public void updateClient(Client client) throws DAOException {
		LOGGER.info("updating client");
		Connection connection = null;
		try {
			connection = cnr.getConnection();
			String sqlRequest = "UPDATE clients set full_name = ?, created_at = ? WHERE client_id = ?";
			PreparedStatement statement = connection.prepareStatement(sqlRequest);
			statement.setString(1, client.full_name());
			statement.setDate(2, client.created_at());
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Can't create statement", e);
			throw new DAOException("Can't create statement", e);
		} catch (InterruptedException e) {
			LOGGER.error("Interupt", e);
			throw new DAOException("Interupt", e);
		} finally {
			closeConnection(connection);
		}
	}

}
