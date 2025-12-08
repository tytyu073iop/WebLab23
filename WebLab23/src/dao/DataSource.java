package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DataSource {
	private static DataSource instance;
	private Connection conn;
	private boolean isOcupied;
	private static final Logger LOGGER = LogManager.getLogger();
    
    private DataSource() throws JDBCConnectionException {
    		ConfigurationManager cfg = ConfigurationManager.getInstance();
    		
    		try {
    			Class.forName(cfg.getDriver());
    			conn = DriverManager.getConnection(cfg.getUrl(), cfg.getLogin(), cfg.getPassword());
    		} catch (ClassNotFoundException e) {
    			LOGGER.error("Can't load database driver.", e);
    			throw new JDBCConnectionException("Can't load database driver.", e);
    		} catch (SQLException e) {
    			LOGGER.error("Can't connect to database.", e);
    			throw new JDBCConnectionException("Can't connect to database.", e);
    		}
    		
    		if(conn == null) {
    			LOGGER.error("Driver type is not correct in URL", cfg.getUrl());
    			throw new JDBCConnectionException("Driver type is not correct in URL " + cfg.getUrl() + ".");
    		}
    		LOGGER.info("Sucessfully created dataSource and connection");
    
    }
    
    public synchronized static DataSource getInstance() throws JDBCConnectionException {
    	LOGGER.info("getting instance");
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }
    
    public synchronized Connection getConnection() throws InterruptedException {
    	LOGGER.info("getting connection");
    	if (isOcupied) {
    		LOGGER.warn("connection is ocupied, waiting");
    		this.wait();
    	}
    	isOcupied = true;
    	return conn;
    }
    
    public synchronized void closeConnection(Connection conn) {
    	LOGGER.info("closing connection");
    	isOcupied = false;
    	this.notify();
    }
}
