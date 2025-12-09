package dao;

import jakarta.persistence.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DataSource {
	private static DataSource instance;
	private EntityManager em = null;
	private boolean isOcupied;
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String ENTITY_MANAGER_FACTORY_NAME = "simpleFactory2";
	private EntityManagerFactory factory;
    
    private DataSource() throws JDBCConnectionException {
    	factory = Persistence.createEntityManagerFactory(ENTITY_MANAGER_FACTORY_NAME);
    	LOGGER.info("Sucessfully created dataSource and entity manager");
    
    }
    
    public synchronized static DataSource getInstance() throws JDBCConnectionException {
    	LOGGER.info("getting instance");
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }
    
    public synchronized EntityManager getEntityManager() throws InterruptedException {
    	LOGGER.info("getting entity manager");
    	if (isOcupied) {
    		LOGGER.warn("entity manager is ocupied, waiting");
    		this.wait();
    	}
    	isOcupied = true;
    	em = factory.createEntityManager();
    	return em;
    }
    
    public synchronized void releaseEntityManager(EntityManager conn) {
    	LOGGER.info("releasing entity manager");
    	conn.close();
    	isOcupied = false;
    	this.notify();
    }
}
