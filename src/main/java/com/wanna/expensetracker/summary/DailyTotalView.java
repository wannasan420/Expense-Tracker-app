package com.wanna.expensetracker.summary;

import java.math.BigDecimal;

public interface DailyTotalView {

	Integer getYear();

    Integer getMonth();
    
    Integer getDay();

    BigDecimal getTotal();
}
