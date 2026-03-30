package com.wanna.expensetracker.summary.dto;

import java.time.LocalDate;
import java.util.List;

import com.wanna.expensetracker.entity.TransactionType;

public record CategorySummaryResponse(
	    LocalDate from,
	    LocalDate to,
	    TransactionType type,
	    List<CategoryTotalResponse> data
	) {}