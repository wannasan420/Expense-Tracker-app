package com.wanna.expensetracker.summary;

import java.math.BigDecimal;

public interface CategoryTotalView {
    String getCategory();
    BigDecimal getTotal();
}