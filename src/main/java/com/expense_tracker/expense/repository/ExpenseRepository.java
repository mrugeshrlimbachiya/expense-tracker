package com.expense_tracker.expense.repository;

import com.expense_tracker.expense.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<Expense> findByExpenseId(String expenseId);
    Page<Expense> findByUserId(String userId, Pageable pageable);

}
