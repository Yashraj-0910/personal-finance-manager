package com.finance.tracker.service;

import com.finance.tracker.dto.ExpenseRequest;
import com.finance.tracker.model.*;
import com.finance.tracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // Get logged in user
    private User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get all expenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findByUser(getCurrentUser());
    }

    // Get expenses by category
    public List<Expense> getByCategory(Long categoryId) {
        User user = getCurrentUser();
        Category category = categoryRepository
                .findByIdAndUser(categoryId, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return expenseRepository.findByUserAndCategory(user, category);
    }

    // Get expenses by date range
    public List<Expense> getByDateRange(LocalDate start, LocalDate end) {
        return expenseRepository.findByUserAndDateBetween(
                getCurrentUser(), start, end);
    }

    // Create expense
    public Expense createExpense(ExpenseRequest request) {
        User user = getCurrentUser();

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository
                    .findByIdAndUser(request.getCategoryId(), user)
                    .orElseThrow(() ->
                            new RuntimeException("Category not found"));
        }

        Expense expense = Expense.builder()
                .title(request.getTitle())
                .amount(request.getAmount())
                .date(request.getDate())
                .note(request.getNote())
                .category(category)
                .user(user)
                .build();

        return expenseRepository.save(expense);
    }

    // Update expense
    public Expense updateExpense(Long id, ExpenseRequest request) {
        User user = getCurrentUser();

        Expense expense = expenseRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository
                    .findByIdAndUser(request.getCategoryId(), user)
                    .orElseThrow(() ->
                            new RuntimeException("Category not found"));
        }

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setDate(request.getDate());
        expense.setNote(request.getNote());
        expense.setCategory(category);

        return expenseRepository.save(expense);
    }

    // Delete expense
    public String deleteExpense(Long id) {
        User user = getCurrentUser();
        Expense expense = expenseRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expenseRepository.delete(expense);
        return "Expense deleted successfully";
    }

    // Get total spending
    public Double getTotalSpending() {
        return expenseRepository.findByUser(getCurrentUser())
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
}