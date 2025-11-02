package dao;

import java.util.ResourceBundle;

public class ConfigurationManager {
	private String url;
	private String login;
	private String password;
	private String driver;
	private ConfigurationManager() {
		
	}
	public static ConfigurationManager getInstance() {
		ResourceBundle resource = ResourceBundle.getBundle("database");
		
		ConfigurationManager cm = new ConfigurationManager();
		cm.url = resource.getString("url");
		cm.login = resource.getString("user");
		cm.password = resource.getString("password");
		cm.driver = resource.getString("driver");
		
		return cm;
	}
	public String getUrl() {
		return url;
	}
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public String getDriver() {
		return driver;
	}
}
