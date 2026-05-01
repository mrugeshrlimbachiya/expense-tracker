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

        return ResponseEntity.ok(expenseService.createExpense(req, userId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ExpensePageResponse> getAllExpenses(
            HttpServletRequest request,
            Pageable pageable) {

        String userId = (String) request.getAttribute("userId");

        return ResponseEntity.ok(expenseService.getAllExpenses(pageable, userId));
    }

    @GetMapping("/getById/{expenseId}")
    public ResponseEntity<Expense> getById(
            HttpServletRequest request,
            @PathVariable String expenseId) {

        String userId = (String) request.getAttribute("userId");

        return ResponseEntity.ok(
                expenseService.getExpenseById(expenseId, userId)
        );
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<String> updateExpense(
            HttpServletRequest request,
            @PathVariable String expenseId,
            @RequestBody UpdateExpenseRequest req) {

        String userId = (String) request.getAttribute("userId");

        return ResponseEntity.ok(
                expenseService.updateExpense(expenseId, req, userId)
        );
    }
}
