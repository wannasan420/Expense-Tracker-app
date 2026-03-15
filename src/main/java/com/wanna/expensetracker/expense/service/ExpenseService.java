package com.wanna.expensetracker.expense.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wanna.expensetracker.entity.Expense;
import com.wanna.expensetracker.exception.ExpenseNotFoundException;
import com.wanna.expensetracker.expense.ExpenseMapper;
import com.wanna.expensetracker.expense.dto.ExpenseCreateRequest;
import com.wanna.expensetracker.expense.dto.ExpenseResponse;
import com.wanna.expensetracker.repo.ExpenseRepo;

@Service
public class ExpenseService {

    private final ExpenseRepo expenseRepo;

    public ExpenseService(ExpenseRepo expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    // GET /expenses
    public List<ExpenseResponse> findAll() {
        return expenseRepo.findAll()
                .stream()
                .map(ExpenseMapper::toResponse)
                .toList();
    } 
    
    public Page<ExpenseResponse> findAll(Pageable pageable) {
        return expenseRepo.findAll(pageable)
                .map(ExpenseMapper::toResponse);
    }

    // GET /expenses/{id}
    public ExpenseResponse findById(Long id) {
        Expense e = expenseRepo.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));
        return ExpenseMapper.toResponse(e);
    }

    // POST /expenses
    public ExpenseResponse create(ExpenseCreateRequest req) {
        Expense e = ExpenseMapper.toEntity(req);
        Expense saved = expenseRepo.save(e);
        return ExpenseMapper.toResponse(saved);
    }

    // PUT /expenses/{id}
    public ExpenseResponse update(Long id, ExpenseCreateRequest req) {
        Expense existing = expenseRepo.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));

        existing.setAmount(req.getAmount());
        existing.setDescription(req.getDescription());
        existing.setCategory(req.getCategory());

        // update spentAt ONLY if client sends it 
        if (req.getSpentAt() != null) {
            existing.setSpentAt(req.getSpentAt());
        }

        Expense saved = expenseRepo.save(existing);
        return ExpenseMapper.toResponse(saved);
    }

    // DELETE /expenses/{id}
    public void delete(Long id) { 
        if (!expenseRepo.existsById(id)) {
            throw new ExpenseNotFoundException(id);
        }
        expenseRepo.deleteById(id);
    }
     
    public List<ExpenseResponse> findByCategory(String category) {
        return expenseRepo.findByCategoryIgnoreCase(category)
                .stream()
                .map(ExpenseMapper::toResponse) 
                .toList(); 
    }
    
    public Page<ExpenseResponse> findByCategory(String category, Pageable pageable) {
        return expenseRepo.findByCategoryIgnoreCase(category, pageable)
                .map(ExpenseMapper::toResponse);
    }
    
    public Page<ExpenseResponse> findBySpentAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return expenseRepo.findBySpentAtBetween(from, to, pageable)
                .map(ExpenseMapper::toResponse);
    }

    public Page<ExpenseResponse> findByCategoryAndSpentAtBetween(
            String category, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return expenseRepo.findByCategoryIgnoreCaseAndSpentAtBetween(category, from, to, pageable)
                .map(ExpenseMapper::toResponse);
    }
}