package com.ecommerce.happyshopping.service;
import com.ecommerce.happyshopping.model.Category;
import com.ecommerce.happyshopping.payload.CategoryDTO;
import com.ecommerce.happyshopping.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,  String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long id);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id);

}
