package com.wanna.expensetracker.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.wanna.expensetracker.entity.TransactionType;

public class ExpenseResponse {
    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private String category;
    private LocalDateTime spentAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ExpenseResponse() {}

    public ExpenseResponse(Long id,TransactionType type, BigDecimal amount, String description, String category,
                           LocalDateTime spentAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.spentAt = spentAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public TransactionType getType(){ return type; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public LocalDateTime getSpentAt() { return spentAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setType(TransactionType type) { this.type = type; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setSpentAt(LocalDateTime spentAt) { this.spentAt = spentAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}