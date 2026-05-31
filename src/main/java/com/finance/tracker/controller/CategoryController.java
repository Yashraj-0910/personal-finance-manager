package com.finance.tracker.controller;

import com.finance.tracker.dto.CategoryRequest;
import com.finance.tracker.model.Category;
import com.finance.tracker.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<Category> create(
            @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.createCategory(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}