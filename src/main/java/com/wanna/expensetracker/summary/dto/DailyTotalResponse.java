package com.wanna.expensetracker.summary.dto;

import java.math.BigDecimal;

public record DailyTotalResponse(
		String day, 
		BigDecimal total
		
) {}
