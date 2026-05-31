package com.finance.tracker.controller;

import com.finance.tracker.dto.BudgetRequest;
import com.finance.tracker.dto.BudgetStatus;
import com.finance.tracker.model.Budget;
import com.finance.tracker.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<Budget> setBudget(
            @Valid @RequestBody BudgetRequest request) {
        return ResponseEntity.ok(budgetService.setBudget(request));
    }

    @GetMapping("/status")
    public ResponseEntity<List<BudgetStatus>> getStatus() {
        return ResponseEntity.ok(budgetService.getAllBudgetStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(budgetService.deleteBudget(id));
    }
}