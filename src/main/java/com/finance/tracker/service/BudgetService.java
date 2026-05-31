package com.finance.tracker.service;

import com.finance.tracker.dto.BudgetRequest;
import com.finance.tracker.dto.BudgetStatus;
import com.finance.tracker.model.*;
import com.finance.tracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Set budget for a category
    public Budget setBudget(BudgetRequest request) {
        User user = getCurrentUser();

        Category category = categoryRepository
                .findByIdAndUser(request.getCategoryId(), user)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Update if exists, create if not
        Budget budget = budgetRepository
                .findByCategoryAndUser(category, user)
                .orElse(Budget.builder()
                        .category(category)
                        .user(user)
                        .build());

        budget.setLimitAmount(request.getLimitAmount());
        return budgetRepository.save(budget);
    }

    // Get all budget statuses
    public List<BudgetStatus> getAllBudgetStatus() {
        User user = getCurrentUser();
        List<Budget> budgets = budgetRepository.findByUser(user);

        return budgets.stream().map(budget -> {
            Double spent = expenseRepository
                    .findByUserAndCategory(user, budget.getCategory())
                    .stream()
                    .mapToDouble(Expense::getAmount)
                    .sum();

            Double remaining = budget.getLimitAmount() - spent;
            boolean exceeded = spent > budget.getLimitAmount();

            return new BudgetStatus(
                    budget.getCategory().getName(),
                    budget.getLimitAmount(),
                    spent,
                    remaining,
                    exceeded
            );
        }).collect(Collectors.toList());
    }

    // Delete budget
    public String deleteBudget(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        budgetRepository.delete(budget);
        return "Budget deleted successfully";
    }
}