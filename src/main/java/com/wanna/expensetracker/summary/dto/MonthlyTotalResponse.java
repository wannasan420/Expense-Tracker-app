package com.wanna.expensetracker.summary.dto;

import java.math.BigDecimal;

public record MonthlyTotalResponse(
        String month,
        BigDecimal total
) {}