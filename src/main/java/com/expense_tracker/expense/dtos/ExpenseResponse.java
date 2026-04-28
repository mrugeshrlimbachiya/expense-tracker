package com.expense_tracker.expense.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponse {

    public Long expenseId;
    public String expenseName;
    public Double amount;
    public LocalDate date;
}
