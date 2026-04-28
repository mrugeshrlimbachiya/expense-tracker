package com.expense_tracker.expense.dtos;

import java.util.List;

public class ExpensePageResponse {

    public List<ExpenseResponse> data;
    public int page;
    public int size;
    public long totalElements;
    public int totalPages;
}
