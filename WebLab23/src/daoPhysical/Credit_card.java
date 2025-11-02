package daoPhysical;

import java.sql.Date;

public record Credit_card (
	int card_id,
	int account_id,
	String card_number,
	Date expiry_date,
	boolean is_active
) {}
