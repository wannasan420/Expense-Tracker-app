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
import com.wanna.expensetracker.entity.TransactionType;
import com.wanna.expensetracker.summary.CategoryTotalView;
import com.wanna.expensetracker.summary.DailyTotalView;
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
	BigDecimal sumAmountBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
	
	@Query("""
		    select coalesce(sum(e.amount), 0)
		    from Expense e
		    where e.spentAt between :from and :to and e.type = :type
		""")
	BigDecimal sumAmountByTypeBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("type") TransactionType type);

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
		    select e.category as category, coalesce(sum(e.amount), 0) as total
		    from Expense e
		    where e.spentAt between :from and :to
		      and e.type = :type
		    group by e.category
		    order by total desc
		""")
	List<CategoryTotalView> sumByCategoryAndTypeBetween(@Param("from") LocalDateTime from,
			@Param("to") LocalDateTime to, @Param("type") TransactionType type);
	
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
	
	@Query("""
			select 
			    year(e.spentAt) as year,
			    month(e.spentAt) as month,
			    coalesce(sum(e.amount),0) as total
			from Expense e
			where e.spentAt between :from and :to
			and e.type = :type
			group by year(e.spentAt), month(e.spentAt)
			order by year, month
			""")
			List<MonthlyTotalView> sumByMonthAndByTypeBetween(
			        @Param("from") LocalDateTime from,
			        @Param("to") LocalDateTime to,
			        @Param("type") TransactionType type
			);
	
	@Query("""
			select 
				year(e.spentAt) as year,
			    month(e.spentAt) as month,
			    day(e.spentAt) as day,
			    coalesce(sum(e.amount),0) as total
			from Expense e 
			where e.spentAt between :from and :to
			group by year(e.spentAt), month(e.spentAt), day(e.spentAt)
			order by year, month, day
			""")
			List<DailyTotalView> sumByDailyBetween(
					@Param("from") LocalDateTime from,
					@Param("to") LocalDateTime to
			);
	
	@Query("""
			select 
				year(e.spentAt) as year,
			    month(e.spentAt) as month,
			    day(e.spentAt) as day,
			    coalesce(sum(e.amount),0) as total
			from Expense e 
			where e.spentAt between :from and :to
			and e.type = :type
			group by year(e.spentAt), month(e.spentAt), day(e.spentAt)
			order by year, month, day
			""")
			List<DailyTotalView> sumByDailyAndTypeBetween(
					@Param("from") LocalDateTime from,
					@Param("to") LocalDateTime to,
					@Param("type") TransactionType type
			);
	
	Page<Expense> findByDescriptionContainingIgnoreCase(String keyword,Pageable pageable);
	
	Page<Expense> findByDescriptionContainingIgnoreCaseAndCategoryIgnoreCase(String keyword, String category, Pageable pageable);
	
	List<Expense> findByType(TransactionType type);
	
	Page<Expense> findByType(TransactionType type, Pageable pageable);
	
	Page<Expense> findByDescriptionContainingIgnoreCaseAndCategoryIgnoreCaseAndType(
	        String keyword, String category, TransactionType type, Pageable pageable);

	Page<Expense> findByDescriptionContainingIgnoreCaseAndType(
	        String keyword, TransactionType type, Pageable pageable);

	Page<Expense> findByCategoryIgnoreCaseAndType(
	        String category, TransactionType type, Pageable pageable);
	
	List<Expense> findByDescriptionContainingIgnoreCase(String keyword);
	
	List<Expense> findByDescriptionContainingIgnoreCaseAndCategoryIgnoreCase(String keyword, String category);

	List<Expense> findByDescriptionContainingIgnoreCaseAndType(String keyword, TransactionType type);

	List<Expense> findByCategoryIgnoreCaseAndType(String category, TransactionType type);

	List<Expense> findBySpentAtBetween(LocalDateTime from, LocalDateTime to);
	
	

	
	
	
	
 
}
