package com.wanna.expensetracker.summary;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanna.expensetracker.entity.TransactionType;
import com.wanna.expensetracker.summary.dto.BalanceResponse;
import com.wanna.expensetracker.summary.dto.CategorySummaryResponse;
import com.wanna.expensetracker.summary.dto.DailySummaryResponse;
import com.wanna.expensetracker.summary.dto.DashboardResponse;
import com.wanna.expensetracker.summary.dto.MonthlySummaryResponse;
import com.wanna.expensetracker.summary.dto.TotalSummaryResponse;

@RestController
@RequestMapping("/summary")
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/total")
    public TotalSummaryResponse total(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) TransactionType type
    ) {
        return summaryService.total(from, to, type);
    }

    @GetMapping("/by-category")
    public CategorySummaryResponse byCategory(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) TransactionType type
    ) {
        return summaryService.byCategory(from, to, type);
    }
    
    @GetMapping("/monthly")
    public MonthlySummaryResponse monthly(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) TransactionType type
    ) {
        return summaryService.monthly(from, to, type);
    }
    
    @GetMapping("/daily")
    public DailySummaryResponse daily(
    		 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
             @RequestParam(required = false) TransactionType type
    		){
    	return summaryService.daily(from, to, type);
    }
    
    @GetMapping("/balance") 
    public BalanceResponse balance(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate to
    ) {
        return summaryService.balance(from, to);
    }
    
    @GetMapping("/dashboard")
    public DashboardResponse dashboard(
    		 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
            ) {
    	return summaryService.dashboard(from, to);
    }
}