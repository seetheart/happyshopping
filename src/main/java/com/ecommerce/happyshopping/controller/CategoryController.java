package com.ecommerce.happyshopping.controller;

import com.ecommerce.happyshopping.model.Category;
import com.ecommerce.happyshopping.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api") // it helps to put the coomon paremeter inhere and remove from the methods. It can also
// be used for actions only and we can remove @GetMapping and place @RequestMapping("", Request.GET)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> allCategories = categoryService.getAllCategories();

        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>("Successfully created", HttpStatus.CREATED);
    }
    @DeleteMapping("/public/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        // alternate way: return ResponseEntity.ok(categoryService.deleteCategory(id));
        // alternate way: return ResponseEntity.status(HttpStatus.OK).body("message")
        String status = categoryService.deleteCategory(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/public/categories/{id}")
    public ResponseEntity<String> updateCategory( @Valid @RequestBody Category category, @PathVariable Long id) {
        Category updatedCategory = categoryService.updateCategory(category, id);
        return new ResponseEntity<>("Category with id: " + updatedCategory,  HttpStatus.OK);
    }
}
