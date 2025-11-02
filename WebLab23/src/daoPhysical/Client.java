package daoPhysical;

import java.sql.Date;

public record Client (
	int client_id,
	String full_name,
	Date created_at
) {}
