package com.finance.tracker.repository;

import com.finance.tracker.model.Category;
import com.finance.tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
    Optional<Category> findByIdAndUser(Long id, User user);
    boolean existsByNameAndUser(String name, User user);
}