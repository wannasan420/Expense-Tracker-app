package com.wanna.expensetracker.summary.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BalanceResponse(
		LocalDate from,
	    LocalDate to,
	    BigDecimal income,
	    BigDecimal expense,
	    BigDecimal balance
	) {}
