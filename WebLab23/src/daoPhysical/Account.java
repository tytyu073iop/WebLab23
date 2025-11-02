package daoPhysical;
import java.sql.Date;

public record Account(
	    int account_id,
	    int client_id,
	    double balance,
	    boolean is_active,
	    Date created_at
) {}