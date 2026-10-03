package com.lyra_tech.lyratech_backend.controller;

import com.lyra_tech.lyratech_backend.entity.Category;
import com.lyra_tech.lyratech_backend.repository.CategoryRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(
            CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    // GET all categories
    @GetMapping
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    // GET category by ID
    @GetMapping("/{id}")
    public Category getCategoryById(
            @PathVariable Integer id) {

        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"));
    }

    // CREATE category
    @PostMapping
    public Category createCategory(
            @RequestBody Category category) {

        return categoryRepository.save(category);
    }

    // UPDATE category
    @PutMapping("/{id}")
    public Category updateCategory(
            @PathVariable Integer id,
            @RequestBody Category category) {

        Category existingCategory =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Category not found"));

        existingCategory.setCategoryName(
                category.getCategoryName());

        return categoryRepository.save(existingCategory);
    }

    // DELETE category
    @DeleteMapping("/{id}")
    public String deleteCategory(
            @PathVariable Integer id) {

        Category existingCategory =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Category not found"));

        categoryRepository.delete(existingCategory);

        return "Category deleted successfully";
    }
}