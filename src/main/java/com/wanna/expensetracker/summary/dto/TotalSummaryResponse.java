package com.wanna.expensetracker.summary.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.wanna.expensetracker.entity.TransactionType;

public record TotalSummaryResponse(
		LocalDate from,
	    LocalDate to,
	    TransactionType type,
	    BigDecimal total) {

}
