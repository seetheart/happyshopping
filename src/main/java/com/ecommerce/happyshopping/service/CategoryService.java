package com.ecommerce.happyshopping.service;
import com.ecommerce.happyshopping.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);


}
