package com.wanna.expensetracker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wanna.expensetracker.entity.Expense; 

public interface ExpenseRepo extends JpaRepository<Expense, Long> {
	
}
 