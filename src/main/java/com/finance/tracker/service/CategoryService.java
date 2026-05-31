package com.finance.tracker.service;

import com.finance.tracker.dto.CategoryRequest;
import com.finance.tracker.model.Category;
import com.finance.tracker.model.User;
import com.finance.tracker.repository.CategoryRepository;
import com.finance.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

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

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findByUser(getCurrentUser());
    }

    // Create category
    public Category createCategory(CategoryRequest request) {
        User user = getCurrentUser();

        if (categoryRepository.existsByNameAndUser(request.getName(), user)) {
            throw new RuntimeException("Category already exists");
        }

        Category category = Category.builder()
                .name(request.getName())
                .user(user)
                .build();

        return categoryRepository.save(category);
    }

    // Delete category
    public String deleteCategory(Long id) {
        User user = getCurrentUser();
        Category category = categoryRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
        return "Category deleted successfully";
    }
}