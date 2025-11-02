package dao;

public class Dao {
	protected JdbcConnector cnr;
	
	public Dao() {
		cnr = new JdbcConnector();
	}
	
	public JdbcConnector getJdbcConnector() {
		return cnr;
	}
}
