package com.wanna.expensetracker.summary.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DashboardResponse(
		LocalDate from,
	    LocalDate to,
		BigDecimal totalIncome,
		BigDecimal totalExpense,
		BigDecimal balance,
		List<CategoryTotalResponse> byCategory   
		) {

}
