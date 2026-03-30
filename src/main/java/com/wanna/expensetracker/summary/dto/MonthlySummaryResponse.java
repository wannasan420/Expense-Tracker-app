package com.wanna.expensetracker.summary.dto;

import java.time.LocalDate;
import java.util.List;

import com.wanna.expensetracker.entity.TransactionType;

public record MonthlySummaryResponse(
		LocalDate from,
	    LocalDate to,
	    TransactionType type,
	    List<MonthlyTotalResponse> data) {

}
