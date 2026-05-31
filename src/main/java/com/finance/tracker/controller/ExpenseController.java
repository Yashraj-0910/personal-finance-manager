package com.finance.tracker.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import com.finance.tracker.dto.ExpenseRequest;
import com.finance.tracker.model.Expense;
import com.finance.tracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExpenseController {

    private final ExpenseService expenseService;
    @GetMapping("/export/csv")
    public void exportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; filename=expenses.csv");

        List<Expense> expenses = expenseService.getAllExpenses();
        PrintWriter writer = response.getWriter();

        // Header row
        writer.println("ID,Title,Amount,Date,Category,Note");

        // Data rows
        for (Expense e : expenses) {
            writer.println(
                    e.getId() + "," +
                            e.getTitle() + "," +
                            e.getAmount() + "," +
                            e.getDate() + "," +
                            (e.getCategory() != null ? e.getCategory().getName() : "None") + "," +
                            (e.getNote() != null ? e.getNote() : "")
            );
        }
        writer.flush();
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAll() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Expense>> getByCategory(
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(expenseService.getByCategory(categoryId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Expense>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end) {
        return ResponseEntity.ok(expenseService.getByDateRange(start, end));
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotal() {
        return ResponseEntity.ok(expenseService.getTotalSpending());
    }

    @PostMapping
    public ResponseEntity<Expense> create(
            @Valid @RequestBody ExpenseRequest request) {
        return ResponseEntity.ok(expenseService.createExpense(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> update(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest request) {
        return ResponseEntity.ok(expenseService.updateExpense(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.deleteExpense(id));


    }

}