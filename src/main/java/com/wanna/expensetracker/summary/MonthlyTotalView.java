package com.wanna.expensetracker.summary;

import java.math.BigDecimal;

public interface MonthlyTotalView {

    Integer getYear();

    Integer getMonth();

    BigDecimal getTotal();

}