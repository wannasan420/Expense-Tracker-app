package com.wanna.expensetracker.expense.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanna.expensetracker.expense.dto.ExpenseCreateRequest;
import com.wanna.expensetracker.expense.dto.ExpenseResponse;
import com.wanna.expensetracker.expense.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> create(
            @Valid @RequestBody ExpenseCreateRequest req
    ) {
        ExpenseResponse created = expenseService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ExpenseResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseCreateRequest req
    ) {
        return expenseService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public List<ExpenseResponse> findAll(
            @RequestParam(required = false) String category 
    ) { 
        if (category == null || category.isBlank()) {
            return expenseService.findAll();
        }
        return expenseService.findByCategory(category);
    }
    
    @GetMapping("/paged")
    public Page<ExpenseResponse> findPaged(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @PageableDefault(size = 10, sort = "spentAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        boolean hasCategory = category != null && !category.isBlank();
        boolean hasRange = from != null && to != null;

        if (hasCategory && hasRange) {
            return expenseService.findByCategoryAndSpentAtBetween(category, from, to, pageable);
        }
        if (hasRange) {
            return expenseService.findBySpentAtBetween(from, to, pageable);
        }
        if (hasCategory) {
            return expenseService.findByCategory(category, pageable);
        }
        return expenseService.findAll(pageable);
    }
} 