package com.finance.tracker.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class BudgetStatus {
    private String categoryName;
    private Double limitAmount;
    private Double spentAmount;
    private Double remainingAmount;
    private boolean isExceeded;
}