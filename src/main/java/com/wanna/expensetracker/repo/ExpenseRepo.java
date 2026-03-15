package com.wanna.expensetracker.repo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wanna.expensetracker.entity.Expense;
import com.wanna.expensetracker.summary.CategoryTotalView;
import com.wanna.expensetracker.summary.MonthlyTotalView;

public interface ExpenseRepo extends JpaRepository<Expense, Long> {
	List<Expense> findByCategoryIgnoreCase(String category);

	Page<Expense> findByCategoryIgnoreCase(String category, Pageable pageable);

	Page<Expense> findBySpentAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

	Page<Expense> findByCategoryIgnoreCaseAndSpentAtBetween(String category, LocalDateTime from, LocalDateTime to,
			Pageable pageable);

	@Query("""
			    select coalesce(sum(e.amount), 0)
			    from Expense e
			    where e.spentAt between :from and :to
			""")
	BigDecimal sumSpentBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

	@Query("""
			    select e.category as category, coalesce(sum(e.amount), 0) as total
			    from Expense e
			    where e.spentAt between :from and :to
			    group by e.category
			    order by total desc 
			""")
	List<CategoryTotalView> sumByCategoryBetween(@Param("from") LocalDateTime from,
			@Param("to") LocalDateTime to);
	
	@Query("""
			select 
			    year(e.spentAt) as year,
			    month(e.spentAt) as month,
			    coalesce(sum(e.amount),0) as total
			from Expense e
			where e.spentAt between :from and :to
			group by year(e.spentAt), month(e.spentAt)
			order by year, month
			""")
			List<MonthlyTotalView> sumByMonthBetween(
			        @Param("from") LocalDateTime from,
			        @Param("to") LocalDateTime to
			);
}
