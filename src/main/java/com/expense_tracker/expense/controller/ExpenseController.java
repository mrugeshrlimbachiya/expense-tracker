package com.expense_tracker.expense.controller;

import com.expense_tracker.expense.dtos.CreateExpenseRequest;
import com.expense_tracker.expense.dtos.ExpensePageResponse;
import com.expense_tracker.expense.dtos.UpdateExpenseRequest;
import com.expense_tracker.expense.entity.Expense;
import com.expense_tracker.expense.service.ExpenseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/create")
    public ResponseEntity<String> createExpense(
            HttpServletRequest request,
            @RequestBody CreateExpenseRequest req) {

        String userId = (String) request.getAttribute("userId");

        String response = expenseService.createExpense(req, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ExpensePageResponse> getAllExpenses(HttpServletRequest request, Pageable pageable) {
        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok(expenseService.getAllExpenses(pageable, userId));
    }

    @GetMapping("/getById/{expenseId}")
    public ResponseEntity<Expense> getById(@PathVariable String expenseId) {

        Expense expense = expenseService.getExpenseById(expenseId);

        return ResponseEntity.ok(expense);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<String> updateExpense(
            @PathVariable String expenseId,
            @RequestBody UpdateExpenseRequest req) {

        String response = expenseService.updateExpense(expenseId, req);

        return ResponseEntity.ok(response);
    }
}
