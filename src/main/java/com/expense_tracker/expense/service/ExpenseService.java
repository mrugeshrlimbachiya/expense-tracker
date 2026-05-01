package com.expense_tracker.expense.service;

import com.expense_tracker.expense.dtos.CreateExpenseRequest;
import com.expense_tracker.expense.dtos.ExpensePageResponse;
import com.expense_tracker.expense.dtos.ExpenseResponse;
import com.expense_tracker.expense.dtos.UpdateExpenseRequest;
import com.expense_tracker.expense.entity.Expense;
import com.expense_tracker.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public String createExpense(CreateExpenseRequest req, String userId) {

        Expense expense = Expense.builder()
                .expenseName(req.expenseName)
                .amount(req.amount)
                .date(req.date)
                .description(req.description)
                .userId(userId)
                .build();

        expenseRepository.save(expense);

        return "Expense created successfully with ID: " + expense.getExpenseId();
    }

    public ExpensePageResponse getAllExpenses(Pageable pageable, String userId) {

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "date")
        );

        Page<Expense> page = expenseRepository.findByUserId(userId, sortedPageable);

        List<ExpenseResponse> list = page.getContent().stream().map(exp -> {
            ExpenseResponse res = new ExpenseResponse();
            res.expenseId = exp.getExpenseId();
            res.expenseName = exp.getExpenseName();
            res.amount = exp.getAmount();
            res.date = exp.getDate();
            return res;
        }).toList();

        ExpensePageResponse response = new ExpensePageResponse();
        response.data = list;
        response.page = page.getNumber();
        response.size = page.getSize();
        response.totalElements = page.getTotalElements();
        response.totalPages = page.getTotalPages();

        return response;
    }

    public Expense getExpenseById(String expenseId, String userId) {

        Expense expense = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        return expense;
    }

    public String updateExpense(String expenseId, UpdateExpenseRequest req, String userId) {

        Expense expense = getExpenseById(expenseId, userId);

        if (req.expenseName != null) {
            expense.setExpenseName(req.expenseName);
        }

        if (req.amount != null) {
            expense.setAmount(req.amount);
        }

        if (req.date != null) {
            expense.setDate(req.date);
        }

        if (req.description != null) {
            expense.setDescription(req.description);
        }

        expenseRepository.save(expense);

        return "Expense updated successfully";
    }
}
