package daoPhysical;

import java.sql.Date;

public record Payment (
	int payment_id,
	int from_account_id,
	int to_account_id,
	double amount,
	Date payment_date,
	String status
) {}
