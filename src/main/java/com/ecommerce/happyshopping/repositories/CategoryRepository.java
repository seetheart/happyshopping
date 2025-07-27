package com.ecommerce.happyshopping.repositories;

import com.ecommerce.happyshopping.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(@NotBlank @Size(min = 5, max = 100, message = "Size should be greater than 5 characters") String categoryName);
}
