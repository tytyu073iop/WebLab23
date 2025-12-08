package dao;

import java.sql.Connection;

public class Dao {
	protected DataSource cnr;
	
	public Dao() throws JDBCConnectionException {
		cnr = DataSource.getInstance();
	}
	
	public Connection getJdbcConnector() throws InterruptedException {
		return cnr.getConnection();
	}
	
	public void closeConnection(Connection conn) {
		cnr.closeConnection(conn);
	}
}
