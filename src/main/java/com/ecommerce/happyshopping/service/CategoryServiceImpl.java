package com.ecommerce.happyshopping.service;

import com.ecommerce.happyshopping.model.Category;
import com.ecommerce.happyshopping.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
//    private final List<Category> categories = new ArrayList<>();
//    private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        categoryRepository.delete(category);
        return "Deleted category";
//        List<Category> categories = categoryRepository.findAll();
        // below was for when database not exited
//        Category category = categories.stream()
//                .filter(cat -> cat.getCategoryId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource now found"));

//        categoryRepository.delete(category);
//        return "Successfully deleted category";
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        // alternate way of doing the same operation in delete category
        //  we could have not used optional but just to show that
        // it can be done this way but above is preferred way

        Category savedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        category.setCategoryId(id);
        categoryRepository.save(category);
        return savedCategory;
    }
}
