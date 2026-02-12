package com.wanna.expensetracker.expense;

import com.wanna.expensetracker.entity.Expense;
import com.wanna.expensetracker.expense.dto.ExpenseCreateRequest;
import com.wanna.expensetracker.expense.dto.ExpenseResponse;

public class ExpenseMapper {

    public static Expense toEntity(ExpenseCreateRequest req) {
        Expense e = new Expense();
        e.setAmount(req.getAmount());
        e.setDescription(req.getDescription());
        e.setCategory(req.getCategory());
        e.setSpentAt(req.getSpentAt()); // can be null -> your @PrePersist sets default
        return e;
    }

    public static ExpenseResponse toResponse(Expense e) {
        return new ExpenseResponse(
                e.getId(),
                e.getAmount(),
                e.getDescription(),
                e.getCategory(),
                e.getSpentAt(), 
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}