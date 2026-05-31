package com.finance.tracker.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BudgetRequest {

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "Limit amount is required")
    @Positive(message = "Limit must be positive")
    private Double limitAmount;
}