package com.wanna.expensetracker.summary;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wanna.expensetracker.entity.TransactionType;
import com.wanna.expensetracker.repo.ExpenseRepo;
import com.wanna.expensetracker.summary.dto.BalanceResponse;
import com.wanna.expensetracker.summary.dto.CategorySummaryResponse;
import com.wanna.expensetracker.summary.dto.CategoryTotalResponse;
import com.wanna.expensetracker.summary.dto.DailySummaryResponse;
import com.wanna.expensetracker.summary.dto.DailyTotalResponse;
import com.wanna.expensetracker.summary.dto.DashboardResponse;
import com.wanna.expensetracker.summary.dto.MonthlySummaryResponse;
import com.wanna.expensetracker.summary.dto.MonthlyTotalResponse;
import com.wanna.expensetracker.summary.dto.TotalSummaryResponse;

@Service
public class SummaryService {
	
	private record DateTimeRange(LocalDateTime from, LocalDateTime to) {}

    private final ExpenseRepo expenseRepo;

    public SummaryService(ExpenseRepo expenseRepo) {
        this.expenseRepo = expenseRepo;
    }
    
    private DateTimeRange resolveRange(LocalDate from, LocalDate to) {
    	if (from != null && to != null && from.isAfter(to)) {
		    throw new IllegalArgumentException("from must be before or equal to to");
		}
        if (from == null || to == null) {
            LocalDate now = LocalDate.now();
            LocalDate firstDay = now.withDayOfMonth(1);
            LocalDate lastDay = now.withDayOfMonth(now.lengthOfMonth());
            return new DateTimeRange(
                    firstDay.atStartOfDay(),
                    lastDay.atTime(23, 59, 59)
            );
        }

        return new DateTimeRange(
                from.atStartOfDay(),
                to.atTime(23, 59, 59)
        );
    }

    public TotalSummaryResponse total(LocalDate from, LocalDate to, TransactionType type) {
  
    	BigDecimal total;
    	DateTimeRange range = resolveRange(from, to);
    	
    	if(type != null ) {
    		total = expenseRepo.sumAmountByTypeBetween(range.from(), range.to(), type);
    	}else {
    		total = expenseRepo.sumAmountBetween(range.from(), range.to());
    	}
        return new TotalSummaryResponse(
        		range.from().toLocalDate(),
        		range.to().toLocalDate(),
        		type,
        		total
        		);
        		
    }

    public CategorySummaryResponse byCategory(LocalDate from, LocalDate to, TransactionType type) {
        DateTimeRange range = resolveRange(from, to);

        List<CategoryTotalResponse> data;

        if (type == null) {
            data = expenseRepo.sumByCategoryBetween(range.from(), range.to()).stream()
                    .map(v -> new CategoryTotalResponse(v.getCategory(), v.getTotal()))
                    .toList();
        } else {
            data = expenseRepo.sumByCategoryAndTypeBetween(range.from(), range.to(), type).stream()
                    .map(v -> new CategoryTotalResponse(v.getCategory(), v.getTotal()))
                    .toList();
        }

        return new CategorySummaryResponse(
                range.from().toLocalDate(),
                range.to().toLocalDate(),
                type,
                data
        );
    }
    
    public MonthlySummaryResponse monthly(LocalDate from, LocalDate to, TransactionType type) {

    	DateTimeRange range = resolveRange(from, to);
    	List<MonthlyTotalResponse> data;
        if(type == null) {
        	data = expenseRepo.sumByMonthBetween(range.from(), range.to())
                .stream()
                .map(r -> new MonthlyTotalResponse(
                        r.getYear() + "-" + String.format("%02d", r.getMonth()),
                        r.getTotal()
                ))
                .toList(); 
        }else {
        	data = expenseRepo.sumByMonthAndByTypeBetween(range.from(), range.to(), type)
        			.stream()
        			.map(r -> new MonthlyTotalResponse(
                            r.getYear() + "-" + String.format("%02d", r.getMonth()),
                            r.getTotal()
                    ))
                    .toList();
        }
        return new MonthlySummaryResponse(
        		range.from().toLocalDate(),
                range.to().toLocalDate(),
                type,
                data); 
        		
    } 

	public DailySummaryResponse daily(LocalDate from, LocalDate to, TransactionType type) {
		
	     DateTimeRange range = resolveRange(from, to);
	     List<DailyTotalResponse> data;
	     if(type == null) {
	    	 data = expenseRepo.sumByDailyBetween(range.from(), range.to())
	 				.stream()
	 				.map(r -> new DailyTotalResponse(
	 						r.getYear() + "-" + String.format("%02d",r.getMonth()) + "-" + String.format("%02d", r.getDay()),
	 						r.getTotal()
	 				))
	 				.toList();
	     }else {
	    	 data = expenseRepo.sumByDailyAndTypeBetween(range.from(), range.to(), type)
	    			 .stream()
	    			 .map(r -> new DailyTotalResponse(
		 						r.getYear() + "-" + String.format("%02d",r.getMonth()) + "-" + String.format("%02d", r.getDay()),
		 						r.getTotal() 
		 				))
	    			 .toList();
		 			 
	     }
	     return new DailySummaryResponse(
	        		range.from().toLocalDate(),
	                range.to().toLocalDate(),
	                type,
	                data); 
	} 
	
	public BalanceResponse balance(LocalDate from, LocalDate to) {
	  
		DateTimeRange range = resolveRange(from, to);      
		
	    	BigDecimal expense = expenseRepo.sumAmountByTypeBetween(range.from(), range.to(), TransactionType.EXPENSE);
	    	BigDecimal income = expenseRepo.sumAmountByTypeBetween(range.from(), range.to(), TransactionType.INCOME);
	    
	    	BigDecimal balance = income.subtract(expense);
	    return new BalanceResponse(range.from().toLocalDate(), range.to().toLocalDate(),income,expense,balance);
		
	}
	
	public DashboardResponse dashboard(LocalDate from, LocalDate to) {
		
		DateTimeRange range = resolveRange(from, to);      
		
		BigDecimal totalIncome = expenseRepo.sumAmountByTypeBetween(range.from(), range.to(), TransactionType.INCOME);
		BigDecimal totalExpense = expenseRepo.sumAmountByTypeBetween(range.from(), range.to(), TransactionType.EXPENSE);
		
		BigDecimal balance = totalIncome.subtract(totalExpense);
	
		List<CategoryTotalResponse> data = expenseRepo.sumByCategoryAndTypeBetween(range.from(), range.to(), TransactionType.EXPENSE).stream()
	    			.map(v -> new CategoryTotalResponse(v.getCategory(), v.getTotal()))
	    			.toList();
		
	    return new DashboardResponse(
	    		range.from().toLocalDate(),
	    		range.to().toLocalDate(),
	    		totalIncome, 
	    		totalExpense,
	    		balance,
	    		data);
		
	}
}