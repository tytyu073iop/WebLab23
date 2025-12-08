package dao;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dao {
	protected DataSource cnr;
	private static final Logger LOGGER = LogManager.getLogger();
	
	public Dao() throws JDBCConnectionException {
		LOGGER.info("Constructing dao", this);
		cnr = DataSource.getInstance();
	}
	
	public Connection getJdbcConnector() throws InterruptedException {
		return cnr.getConnection();
	}
	
	public void closeConnection(Connection conn) {
		cnr.closeConnection(conn);
	}
}
