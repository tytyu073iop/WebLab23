package dao;

import jakarta.persistence.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dao {
	protected DataSource cnr;
	private static final Logger LOGGER = LogManager.getLogger();
	
	public Dao() throws JDBCConnectionException {
		LOGGER.info("Constructing dao", this);
		cnr = DataSource.getInstance();
	}
	
	public EntityManager getJdbcConnector() throws InterruptedException {
		return cnr.getEntityManager();
	}
	
	public void closeConnection(EntityManager conn) {
		cnr.releaseEntityManager(conn);
	}
}
