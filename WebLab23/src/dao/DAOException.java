package dao;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1031847059109199548L;

	// Add constructors
    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
