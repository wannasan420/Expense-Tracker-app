package com.wanna.expensetracker.summary;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wanna.expensetracker.repo.ExpenseRepo;
import com.wanna.expensetracker.summary.dto.CategoryTotalResponse;
import com.wanna.expensetracker.summary.dto.MonthlyTotalResponse;
import com.wanna.expensetracker.summary.dto.TotalResponse;

@Service
public class SummaryService {

    private final ExpenseRepo expenseRepo;

    public SummaryService(ExpenseRepo expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    public TotalResponse total(LocalDateTime from, LocalDateTime to) {
        BigDecimal total = expenseRepo.sumSpentBetween(from, to);
        return new TotalResponse(total);
    }

    public List<CategoryTotalResponse> byCategory(LocalDateTime from, LocalDateTime to) {
        return expenseRepo.sumByCategoryBetween(from, to).stream()
                .map(v -> new CategoryTotalResponse(v.getCategory(), v.getTotal()))
                .toList();
    }
    
    public List<MonthlyTotalResponse> monthly(LocalDate from, LocalDate to) {

        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atTime(23, 59, 59);

        return expenseRepo.sumByMonthBetween(fromDateTime, toDateTime)
                .stream()
                .map(r -> new MonthlyTotalResponse(
                        r.getYear() + "-" + String.format("%02d", r.getMonth()),
                        r.getTotal()
                ))
                .toList();
    }
}