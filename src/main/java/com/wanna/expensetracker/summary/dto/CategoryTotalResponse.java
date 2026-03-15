package com.wanna.expensetracker.summary.dto;

import java.math.BigDecimal;

public record CategoryTotalResponse(String category, BigDecimal total) {}