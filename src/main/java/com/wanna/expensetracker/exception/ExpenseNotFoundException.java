package com.wanna.expensetracker.exception;

public class ExpenseNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public ExpenseNotFoundException(Long id) {
        super("Expense not found: " + id);
    }
}