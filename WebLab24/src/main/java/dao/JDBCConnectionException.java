package dao;

public class JDBCConnectionException extends Exception {

	private static final long serialVersionUID = -7253313684940170173L;

	 // Add constructors
    public JDBCConnectionException(String message) {
        super(message);
    }

    public JDBCConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDBCConnectionException(Throwable cause) {
        super(cause);
    }
}
