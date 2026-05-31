package com.finance.tracker.repository;

import com.finance.tracker.model.Expense;
import com.finance.tracker.model.User;
import com.finance.tracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByUserAndCategory(User user, Category category);
    List<Expense> findByUserAndDateBetween(User user,
                                           LocalDate start,
                                           LocalDate end);
    Optional<Expense> findByIdAndUser(Long id, User user);
}