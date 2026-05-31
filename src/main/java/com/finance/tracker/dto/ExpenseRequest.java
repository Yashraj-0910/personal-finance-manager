package com.finance.tracker.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ExpenseRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private String note;

    private Long categoryId;
}